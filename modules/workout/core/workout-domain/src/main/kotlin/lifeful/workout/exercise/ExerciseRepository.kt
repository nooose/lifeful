package lifeful.workout.exercise

import lifeful.shared.id.ExerciseId

interface ExerciseRepository {
    fun save(exercise: Exercise): Exercise

    fun findAll(): List<Exercise>

    fun findById(id: ExerciseId): Exercise?

    fun findByIds(ids: List<ExerciseId>): List<Exercise>

    fun findByName(name: String): Exercise?
}
