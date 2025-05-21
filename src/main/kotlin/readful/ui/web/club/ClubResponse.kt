package readful.ui.web.club

import readful.core.domain.club.Club
import java.time.LocalDateTime

data class ClubResponse(
    val id: Long,
    val title: String,
    val description: String,
    val memberCount: Int,
    val startAt: LocalDateTime,
    val booKTitle: String
) {
    companion object {
        fun from(club: Club): ClubResponse = ClubResponse(
            id = club.id.value,
            title = club.title,
            description = club.description,
            memberCount = club.memberCount,
            startAt = club.startAt,
            booKTitle = club.book.title
        )
    }
}