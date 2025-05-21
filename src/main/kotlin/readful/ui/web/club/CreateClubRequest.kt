package readful.ui.web.club

import java.time.LocalDateTime

data class CreateClubRequest(
    val title: String,
    val description: String,
    val memberCount: Int,
    val startAt: LocalDateTime,
    val bookId: Long
) {
}