package lifeful.member

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class MemberTest : StringSpec({

    lateinit var member: Member

    beforeSpec {
        member = Member.register(
            email = Email("test@test.com"),
            nickname = "testUser",
            password = "testPassword",
            passwordEncoder = TestPasswordEncoder(),
        )
    }

    "사용자를 생성한다." {
        member.status shouldBe MemberStatus.ACTIVE
    }

    "활성화 테스트" {
        member.activate()

        member.isActive.shouldBeTrue()
    }

    "비활성화 테스트" {
        member.deactivate()

        member.isActive.shouldBeFalse()
    }
})
