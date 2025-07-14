package lifeful.workout

import lifeful.shared.id.ExerciseId

interface ExerciseRepository {
    fun add(exercise: Exercise): Exercise

    fun update(exercise: Exercise): Exercise

    fun findAll(): List<Exercise>

    fun findById(id: ExerciseId): Exercise?
}
