package readful.core.domain.club

import jakarta.persistence.*
import readful.core.domain.shared.BaseEntity
import readful.core.domain.shared.ClubId

@Entity
class Club(
    var title: String,
    var description: String,
    var memberMaxCount: Int,
    var hostId: Int,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: ClubId = ClubId(),
    members: List<ClubMember> = emptyList(),
) : BaseEntity() {

    @OneToMany
    val members: MutableList<ClubMember> = members.toMutableList()

    init {
        require(memberMaxCount > 0) { "정원은 1명 이상이여야 합니다." }
    }

    fun requestJoin(memberId: Int) {
        check(!isMemberAlreadyJoined(memberId)) {
            "이미 요청 중이거나 참여 중인 멤버입니다."
        }

        val pendingMember = ClubMember.pending(clubId = this.id, memberId = memberId)
        this.members.add(pendingMember)
    }


    fun acceptMember(memberId: Int, hostId: Int) {
        checkHost(hostId)
        check(this.memberMaxCount > getAcceptedMemberSize()) {
            "정원을 초과할 수 없습니다."
        }

        val clubMember = getMember(memberId)
        clubMember.accept()
    }

    private fun getAcceptedMemberSize(): Int {
        return members.filter { it.state == ClubMemberState.ACCEPTED }.size
    }

    fun rejectMember(memberId: Int, hostId: Int) {
        checkHost(hostId)
        val clubMember = getMember(memberId)
        clubMember.reject()
    }

    fun kickMember(memberId: Int, hostId: Int) {
        checkHost(hostId)

        val clubMember = getMember(memberId)
        this.members.remove(clubMember)
    }

    fun delegateHost(memberId: Int, hostId: Int) {
        checkHost(hostId)

        val newHostMember = getMember(memberId)

        check(newHostMember.isAccepted()) {
            "참여중인 멤버에게만 권한을 위임할 수 있습니다."
        }

        if (this.members.none { it.memberId == hostId }) {
            val previousHostMember = ClubMember.accepted(this.id, hostId)
            this.members.add(previousHostMember)
        }

        this.hostId = memberId
    }

    fun leaveClub(memberId: Int) {
        val member = getMember(memberId)

        check(member.isAccepted()) {
            "참여중인 멤버만 탈퇴할 수 있습니다."
        }

        this.members.remove(member)
    }

    private fun checkHost(hostId: Int) {
        check(this.hostId == hostId) { "클럽 호스트만 처리할 수 있습니다." }
    }

    private fun getMember(memberId: Int): ClubMember {
        return this.members.firstOrNull { it.memberId == memberId }
            ?: throw IllegalStateException("참가자($memberId)를 찾을 수 없습니다.")
    }

    private fun isMemberAlreadyJoined(memberId: Int): Boolean {
        return this.members.any { it.memberId == memberId && it.state != ClubMemberState.REJECTED }
    }
}