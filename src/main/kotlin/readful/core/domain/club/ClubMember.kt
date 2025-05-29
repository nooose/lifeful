package readful.core.domain.club

import jakarta.persistence.Entity
import jakarta.persistence.Id
import readful.core.domain.shared.ClubId

@Entity
class ClubMember(
    val clubId: ClubId,
    val memberId: Int,
    var state: ClubMemberState,
    @Id
    val id: Long = 0L
) {

    fun accept() {
        check(this.state == ClubMemberState.PENDING) {
            "이미 승인되었거나 거절된 멤버는 승인할 수 없습니다."
        }

        this.state = ClubMemberState.ACCEPTED
    }

    fun isAccepted(): Boolean {
        return this.state == ClubMemberState.ACCEPTED
    }

    fun reject() {
        check(this.state == ClubMemberState.PENDING) {
            "이미 승인되었거나 거절된 멤버는 승인할 수 없습니다."
        }

        this.state = ClubMemberState.REJECTED
    }

    companion object {
        fun pending(clubId: ClubId, memberId: Int): ClubMember {
            return ClubMember(
                clubId = clubId,
                memberId = memberId,
                state = ClubMemberState.PENDING
            )
        }
    }
}