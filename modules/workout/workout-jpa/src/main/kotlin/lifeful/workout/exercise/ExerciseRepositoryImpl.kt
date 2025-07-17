package lifeful.workout.exercise

import lifeful.shared.id.ExerciseId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class ExerciseRepositoryImpl(
    private val jpaRepository: ExerciseJpaRepository,
) : ExerciseRepository {
    override fun save(exercise: Exercise): Exercise {
        val entity = ExerciseEntity.from(exercise)
        jpaRepository.save(entity)
        return entity.toDomain()
    }

    override fun findAll(): List<Exercise> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: ExerciseId): Exercise? {
        return jpaRepository.findByIdOrNull(id.value)?.toDomain()
    }

    override fun findByIds(ids: List<ExerciseId>): List<Exercise> {
        return jpaRepository.findAllById(ids.map { it.value })
            .map { it.toDomain() }
    }

    override fun findByName(name: String): Exercise? {
        return jpaRepository.findByName(name)?.toDomain()
    }
}
