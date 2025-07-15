package lifeful.workout.exercise

import lifeful.shared.id.ExerciseId

interface ExerciseRepository {
    fun add(exercise: Exercise): Exercise

    fun update(exercise: Exercise): Exercise

    fun findAll(): List<Exercise>

    fun findById(id: ExerciseId): Exercise?

    fun findByName(name: String): Exercise?
}
