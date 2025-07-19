package lifeful.workout.routine

import jakarta.persistence.Embeddable
import lifeful.shared.id.ExerciseId

@Embeddable
data class RoutineItem(
    val exerciseId: ExerciseId,
    val itemOrder: Int,
)
