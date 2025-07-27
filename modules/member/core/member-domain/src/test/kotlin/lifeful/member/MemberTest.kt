package lifeful.member

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class MemberTest : StringSpec({
    lateinit var member: Member

    beforeEach { // 각 테스트 전에 새로운 Member 인스턴스 생성
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

    "닉네임을 변경한다." {
        val newNickname = "newNickname"
        member.changeNickname(newNickname)
        member.nickname shouldBe newNickname
    }

    "비활성화된 사용자는 닉네임을 변경할 수 없다." {
        member.deactivate()
        val newNickname = "anotherNickname"
        shouldThrow<MemberAccessDeniedException> {
            member.changeNickname(newNickname)
        }
    }
})
