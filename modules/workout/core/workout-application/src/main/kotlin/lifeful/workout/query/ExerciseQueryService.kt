package lifeful.workout.query

import lifeful.shared.id.ExerciseId
import lifeful.workout.Exercise
import lifeful.workout.ExerciseNotFoundException
import lifeful.workout.ExerciseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ExerciseQueryService(
    private val repository: ExerciseRepository,
) {
    fun byId(id: ExerciseId): Exercise {
        return repository.findById(id)
            ?: throw ExerciseNotFoundException("운동 종목($id)을 찾을 수 없습니다.")
    }

    fun all(): List<Exercise> {
        return repository.findAll()
    }
}
