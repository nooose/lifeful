package lifeful.workout.routine.command

import lifeful.shared.id.ExerciseId

data class RoutineItemCreateCommand(
    val exerciseId: ExerciseId,
    val itemOrder: Int
)
