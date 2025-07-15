package lifeful.workout

import lifeful.shared.id.ExerciseId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class ExerciseRepositoryImpl(
    private val jpaRepository: ExerciseJpaRepository,
) : ExerciseRepository {
    override fun add(exercise: Exercise): Exercise {
        val entity = ExerciseEntity.from(exercise)
        jpaRepository.save(entity)
        return entity.toDomain()
    }

    override fun update(exercise: Exercise): Exercise {
        val entity = ExerciseEntity.from(exercise)
        jpaRepository.save(entity)
        return entity.toDomain()
    }

    override fun findAll(): List<Exercise> {
        return jpaRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: ExerciseId): Exercise? {
        return jpaRepository.findByIdOrNull(id)?.toDomain()
    }

    override fun findByName(name: String): Exercise? {
        return jpaRepository.findByName(name)?.toDomain()
    }
}
