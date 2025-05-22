package readful.core.domain.club.reading

import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import java.time.LocalDate

@Embeddable
data class StepPeriod(
    val startDate: LocalDate,
    val endDate: LocalDate
) {
}