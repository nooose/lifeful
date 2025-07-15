package lifeful.workout.exercise.command

import lifeful.workout.exercise.ExerciseCategory
import lifeful.workout.exercise.MuscleGroup

data class ExerciseCreateCommand(
    val name: String,
    val category: ExerciseCategory,
    val muscleGroups: Set<MuscleGroup>,
)
