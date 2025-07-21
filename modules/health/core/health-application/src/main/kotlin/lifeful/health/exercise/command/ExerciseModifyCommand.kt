package lifeful.health.exercise.command

import lifeful.health.exercise.ExerciseCategory
import lifeful.health.exercise.MuscleGroup

data class ExerciseModifyCommand(
    val name: String,
    val category: ExerciseCategory,
    val muscleGroups: Set<MuscleGroup>,
)
