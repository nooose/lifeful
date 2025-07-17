package lifeful.workout.exercise.command

import lifeful.shared.exception.DuplicateException
import lifeful.shared.id.ExerciseId
import lifeful.workout.exercise.Exercise
import lifeful.workout.exercise.ExerciseNotFoundException
import lifeful.workout.exercise.ExerciseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ExerciseCommandService(
    private val repository: ExerciseRepository,
) {
    fun add(command: ExerciseCreateCommand): Exercise {
        validateDuplicateName(command.name)
        val exercise = Exercise(
            name = command.name,
            category = command.category,
            muscleGroups = command.muscleGroups,
        )
        return repository.add(exercise)
    }

    fun modify(
        id: ExerciseId,
        command: ExerciseModifyCommand,
    ) {
        validateDuplicateName(command.name)
        val exercise = repository.findById(id)
            ?: throw ExerciseNotFoundException("운동 종목($id)을 찾을 수 없습니다.")
        val modifiedExercise = exercise.modify(
            name = command.name,
            category = command.category,
            muscleGroups = command.muscleGroups,
        )
        repository.update(modifiedExercise)
    }

    private fun validateDuplicateName(name: String) {
        repository.findByName(name)?.let {
            throw DuplicateException("중복된 운동 ${name}이(가) 존재합니다.")
        }
    }
}
