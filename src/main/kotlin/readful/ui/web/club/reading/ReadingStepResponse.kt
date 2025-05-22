package readful.ui.web.club.reading

import readful.core.domain.club.reading.StepPeriod
import java.time.LocalDate

data class ReadingStepResponse(
    val stepOrder: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val chapterTitles: List<String>
) {
}