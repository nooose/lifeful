package lifeful.workout.routine

import lifeful.shared.id.ExerciseId

data class RoutineItem(
    val exerciseId: ExerciseId,
    val order: Int,
    val id: Long = 0L,
)
