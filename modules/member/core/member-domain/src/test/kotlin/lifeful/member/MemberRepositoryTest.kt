package lifeful.member

import io.kotest.core.spec.style.StringSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.longs.shouldNotBeZero
import io.mockk.every
import io.mockk.mockk
import jakarta.persistence.EntityManager
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MemberRepositoryTest(
    private val repository: MemberRepository,
    private val entityManager: EntityManager,
) : StringSpec({
    extensions(SpringExtension)

    val passwordEncoder = mockk<PasswordEncoder>()

    "회원을 저장하면 회원 상세 정보도 같이 저장된다." {
        every { passwordEncoder.encode(any()) } returns "passwordHash"

        val member = Member.register(
            email = Email("test@test.com"),
            nickname = "테스트",
            password = "test",
            passwordEncoder = passwordEncoder,
        )

        repository.save(member)
        entityManager.flush()
        entityManager.clear()

        val findMember = repository.findById(member.id)!!
        findMember.detail.id.shouldNotBeZero()
    }
})