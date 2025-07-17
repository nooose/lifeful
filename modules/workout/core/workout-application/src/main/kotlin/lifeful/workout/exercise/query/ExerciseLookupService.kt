package lifeful.workout.exercise.query

import lifeful.shared.id.ExerciseId
import lifeful.workout.exercise.Exercise
import lifeful.workout.exercise.ExerciseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class ExerciseLookupService(
    private val exerciseRepository: ExerciseRepository,
) {
    fun byIds(ids: List<ExerciseId>): List<Exercise> {
        return exerciseRepository.findByIds(ids)
    }
}