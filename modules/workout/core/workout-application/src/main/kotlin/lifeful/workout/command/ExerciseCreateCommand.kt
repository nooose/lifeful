package lifeful.workout.command

import lifeful.workout.ExerciseCategory
import lifeful.workout.MuscleGroup

data class ExerciseCreateCommand(
    val name: String,
    val category: ExerciseCategory,
    val muscleGroups: Set<MuscleGroup>,
)
