package lifeful.member

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.string.shouldNotBeBlank
import lifeful.base.IntegrationTest
import lifeful.member.command.MemberLogin
import lifeful.member.command.MemberLoginCommand
import lifeful.member.command.MemberRegister
import lifeful.shared.exception.DuplicateException
import lifeful.shared.id.MemberId
import org.springframework.context.annotation.Import

@Import(MemberTestConfiguration::class)
@IntegrationTest
class MemberManagerTest(
    private val memberRegister: MemberRegister,
    private val memberLogin: MemberLogin,
) : BehaviorSpec({
        extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

        Given("기존 이메일이 가입되어있고") {
            memberRegister.register(MemberFixtures.registerFixtures(email = "test@test.com"))
            When("같은 이메일로 회원가입을 요청하면") {
                Then("예외를 던진다.") {
                    shouldThrow<DuplicateException> {
                        memberRegister.register(MemberFixtures.registerFixtures(email = "test@test.com"))
                    }
                }
            }
        }

        Given("회원 가입 후") {
            val member = memberRegister.register(
                MemberFixtures.registerFixtures(
                    email = "test@test.com",
                    password = "test",
                ),
            )
            When("로그인을 하면") {
                val token = memberLogin.getToken(
                    MemberLoginCommand(
                        email = member.email.address,
                        password = "test",
                    ),
                )
                Then("토큰을 발급받는다.") {
                    token.shouldNotBeBlank()
                }
            }

            When("로그인할 때 이메일 정보가 일치하지 않으면") {
                Then("토큰을 발급받지 못한다.") {
                    shouldThrow<MemberAuthenticationFailedException> {
                        memberLogin.getToken(
                            MemberLoginCommand(
                                email = "invalid@test.com",
                                password = "test",
                            ),
                        )
                    }
                }
            }

            When("로그인할 때 비밀번호가 일치하지 않으면") {
                Then("토큰을 발급받지 못한다.") {
                    shouldThrow<MemberAuthenticationFailedException> {
                        memberLogin.getToken(
                            MemberLoginCommand(
                                email = member.email.address,
                                password = "test1",
                            ),
                        )
                    }
                }
            }

            When("비활성화 유저가 로그인을 하면") {
                memberRegister.deactivate(MemberId(member.id))
                Then("토큰을 발급받지 못한다.") {
                    shouldThrow<MemberAccessDeniedException> {
                        memberLogin.getToken(
                            MemberLoginCommand(
                                email = "test@test.com",
                                password = "test",
                            ),
                        )
                    }
                }
            }
        }
    })
