package lifeful.health.exercise

import org.springframework.data.repository.Repository

interface ExerciseRepository : Repository<Exercise, Long> {
    fun save(exercise: Exercise): Exercise

    fun findAll(): List<Exercise>

    fun findById(id: Long): Exercise?

    fun findByName(name: String): Exercise?

    fun findAllByIdIn(ids: List<Long>): List<Exercise>
}
