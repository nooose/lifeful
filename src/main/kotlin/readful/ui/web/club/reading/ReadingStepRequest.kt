package readful.ui.web.club.reading

import java.time.LocalDate

data class ReadingStepRequest(
    val stepOrder: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val chapterIds: List<Long>,
)
