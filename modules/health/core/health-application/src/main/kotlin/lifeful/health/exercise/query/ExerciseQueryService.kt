package lifeful.health.exercise.query

import lifeful.health.exercise.Exercise
import lifeful.health.exercise.ExerciseCacheRepository
import lifeful.health.exercise.ExerciseNotFoundException
import lifeful.health.exercise.ExerciseRepository
import lifeful.shared.id.ExerciseId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
internal class ExerciseQueryService(
    private val repository: ExerciseRepository,
    private val cacheRepository: ExerciseCacheRepository,
) : ExerciseFinder {
    override fun get(id: ExerciseId): Exercise {
        return repository.findById(id.value)
            ?: throw ExerciseNotFoundException("운동 종목($id)을 찾을 수 없습니다.")
    }

    override fun get(ids: List<ExerciseId>): List<Exercise> {
        return repository.findAllByIdIn(ids = ids.map { it.value })
    }

    override fun all(): List<Exercise> {
        val cachedExercises = cacheRepository.getAll()
        if (cachedExercises.isEmpty()) {
            val exercises = repository.findAll()
            cacheRepository.putAll(exercises)
            return exercises
        }
        return cachedExercises
    }
}
