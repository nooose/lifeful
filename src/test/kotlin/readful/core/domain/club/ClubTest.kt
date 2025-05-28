package readful.core.domain.club

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import readful.core.domain.shared.ClubId

class ClubTest : StringSpec({

    "클럽을 생성할 수 있다." {
        shouldNotThrowAny {
            Club(
                title = "독서 멤버 구합니다.",
                description = "꾸준히 하실 분만",
                memberMaxCount = 4,
                hostId = 1
            )
        }
    }

    "정원이 0명 이하이면 예외가 발생한다." {
        shouldThrowAny {
            Club(
                title = "제목",
                description = "설명",
                memberMaxCount = 0,
                hostId = 1
            )
        }
    }

    "이미 정원이 가득 찼을 때, 호스트가 승인하면 예외가 발생한다." {
        val club = Club(
            title = "제목",
            description = "설명",
            memberMaxCount = 4,
            members = listOf(
                ClubMember(ClubId(1), 1, ClubMemberState.ACCEPTED),
                ClubMember(ClubId(1), 2, ClubMemberState.ACCEPTED),
                ClubMember(ClubId(1), 3, ClubMemberState.ACCEPTED),
                ClubMember(ClubId(1), 4, ClubMemberState.ACCEPTED),
            ),
            hostId = 1
        )

        val exception = shouldThrowAny {
            club.acceptMember(memberId = 5, hostId = 1)
        }

        exception.message shouldBe "정원을 초과할 수 없습니다."
    }

    "참여 요청 시, 멤버의 상태는 PENDING 이 된다." {
        val club = Club("제목", "설명", 3, hostId = 1)
        club.requestJoin(memberId = 2)

        val member = club.members.first { it.memberId == 2 }
        member.state shouldBe ClubMemberState.PENDING
    }

    "호스트가 참여 요청을 거부하면, 멤버의 상태는 REJECTED 가 된다." {
        val club = Club("제목", "설명", 3, hostId = 1)
        club.requestJoin(memberId = 2)
        club.rejectMember(memberId = 2, hostId = 1)

        val member = club.members.first { it.memberId == 2}
        member.state shouldBe ClubMemberState.REJECTED
    }
})