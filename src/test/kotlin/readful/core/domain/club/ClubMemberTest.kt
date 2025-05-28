package readful.core.domain.club

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import readful.core.domain.shared.ClubId

class ClubMemberTest : StringSpec({

    "PENDING 상태인 멤버는 승인할 수 있다." {
        val member = ClubMember(
            clubId = ClubId(1),
            memberId = 1,
            state = ClubMemberState.PENDING
        )

        member.accept()

        member.state shouldBe ClubMemberState.ACCEPTED
    }

    "PENDING 상태인 멤버는 거절할 수 있다." {
        val member = ClubMember(
            clubId = ClubId(2),
            memberId = 1,
            state = ClubMemberState.PENDING
        )

        member.reject()

        member.state shouldBe ClubMemberState.REJECTED
    }

    "이미 승인된 멤버는 승인할 수 없다." {
        val member = ClubMember(
            clubId = ClubId(2),
            memberId = 1,
            state = ClubMemberState.ACCEPTED
        )

        val exception = shouldThrow<IllegalStateException> {
            member.accept()
        }

        exception.message shouldBe "이미 승인되었거나 거절된 멤버는 승인할 수 없습니다."
    }

    "이미 거절된 멤버는 승인할 수 없다." {
        val member = ClubMember(
            clubId = ClubId(2),
            memberId = 1,
            state = ClubMemberState.REJECTED
        )

        val exception = shouldThrow<IllegalStateException> {
            member.accept()
        }

        exception.message shouldBe "이미 승인되었거나 거절된 멤버는 승인할 수 없습니다."
    }


})