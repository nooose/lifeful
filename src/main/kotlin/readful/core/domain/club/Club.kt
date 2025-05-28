package readful.core.domain.club

import jakarta.persistence.*
import readful.core.domain.book.Book
import readful.core.domain.book.BookChapter
import readful.core.domain.club.reading.ReadingStep
import readful.core.domain.shared.BaseEntity
import readful.core.domain.shared.ClubId

@Entity
class Club(
    var title: String,
    var description: String,
    var memberMaxCount: Int,
    val hostId: Int,
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
        val pendingMember = ClubMember.pending(clubId = this.id, memberId = memberId)
        this.members.add(pendingMember)
    }
}