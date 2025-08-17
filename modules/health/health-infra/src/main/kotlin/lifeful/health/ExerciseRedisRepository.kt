package lifeful.health

import lifeful.health.exercise.Exercise
import lifeful.health.exercise.ExerciseCacheRepository
import org.springframework.stereotype.Repository

@Repository
internal class ExerciseRedisRepository(
    private val crudRepository: ExerciseCrudRepository,
) : ExerciseCacheRepository {
    override fun putAll(exercises: List<Exercise>) {
        crudRepository.deleteAll()
        if (exercises.isEmpty()) return
        val caches = exercises.map { ExerciseCache.from(it) }
        crudRepository.saveAll(caches)
    }

    override fun getAll(): List<Exercise> {
        return crudRepository.findAll()
            .map { it.toDomain() }
            .toList()
    }

    override fun deleteAll() {
        crudRepository.deleteAll()
    }
}
