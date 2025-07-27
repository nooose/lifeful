package lifeful.member

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.core.annotation.DisplayName
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import java.nio.charset.StandardCharsets
import lifeful.base.CustomMvcTester
import lifeful.base.IntegrationTest
import org.assertj.core.api.Assertions.assertThat
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType

@DisplayName(name = "인증 인수 테스트")
@Import(MemberTestConfiguration::class)
@IntegrationTest
class AuthenticationApiTest(
    private val authApi: AuthApi,
    private val memberApi: MemberApi,
    private val objectMapper: ObjectMapper,
) : BehaviorSpec({
        extensions(SpringTestExtension(SpringTestLifecycleMode.Root))

        val apiTester = CustomMvcTester.mvcTester(memberApi, authApi)

        Given("회원가입을 하고") {
            assertThat(
                apiTester.post()
                    .uri("/api/v1/members")
                    .content(
                        objectMapper.writeValueAsString(
                            MemberRegisterRequest(
                                email = "test@test.com",
                                nickname = "테스트",
                                password = "test",
                            ),
                        ),
                    )
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .exchange(),
            ).hasStatusOk()
            When("로그인을 하면") {
                val tokenResult = apiTester.post()
                    .uri("/api/v1/auth/token")
                    .content(
                        objectMapper.writeValueAsString(
                            TokenRequest(
                                email = "test@test.com",
                                password = "test",
                            ),
                        ),
                    )
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)
                    .exchange()

                Then("토큰을 발급받는다.") {
                    assertThat(tokenResult).hasStatusOk()
                }
            }

            When("인증 정보가 일치하지 않으면") {
                val tokenResult = apiTester.post()
                    .uri("/api/v1/auth/token")
                    .content(
                        objectMapper.writeValueAsString(
                            TokenRequest(
                                email = "test@test.com",
                                password = "invalid",
                            ),
                        ),
                    )
                    .characterEncoding(StandardCharsets.UTF_8)
                    .contentType(MediaType.APPLICATION_JSON)

                Then("토큰을 발급받지 못한다.") {
                    assertThat(tokenResult)
                        .hasFailed()
                        .failure()
                        .cause()
                        .isInstanceOf(MemberAuthenticationFailedException::class.java)
                }
            }
        }
    })
