package readful.ui.web.club

import readful.ui.web.club.reading.ReadingStepRequest
import java.time.LocalDateTime

data class CreateClubRequest(
    val title: String,
    val description: String,
    val memberCount: Int,
    val startAt: LocalDateTime,
    val bookId: Long,
    val steps: List<ReadingStepRequest>
) {
}