package lifeful.health.exercise.command

import lifeful.health.exercise.ExerciseCategory
import lifeful.health.exercise.MuscleGroup

data class ExerciseCreateCommand(
    val name: String,
    val category: ExerciseCategory,
    val muscleGroups: Set<MuscleGroup>,
)
