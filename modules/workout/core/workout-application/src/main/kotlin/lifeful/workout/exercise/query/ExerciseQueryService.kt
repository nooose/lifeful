package lifeful.workout.exercise.query

import lifeful.shared.id.ExerciseId
import lifeful.workout.exercise.Exercise
import lifeful.workout.exercise.ExerciseNotFoundException
import lifeful.workout.exercise.ExerciseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
internal class ExerciseQueryService(
    private val repository: ExerciseRepository,
) : ExerciseFinder {
    override fun get(id: ExerciseId): Exercise {
        return repository.findById(id.value)
            ?: throw ExerciseNotFoundException("운동 종목($id)을 찾을 수 없습니다.")
    }

    override fun get(ids: List<ExerciseId>): List<Exercise> {
        return repository.findAllByIdIn(ids = ids.map { it.value })
    }

    override fun all(): List<Exercise> {
        return repository.findAll()
    }
}
