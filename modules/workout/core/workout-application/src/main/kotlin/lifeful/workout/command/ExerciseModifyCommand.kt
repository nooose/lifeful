package lifeful.workout.command

import lifeful.workout.ExerciseCategory
import lifeful.workout.MuscleGroup

data class ExerciseModifyCommand(
    val name: String,
    val category: ExerciseCategory,
    val muscleGroups: Set<MuscleGroup>,
)
