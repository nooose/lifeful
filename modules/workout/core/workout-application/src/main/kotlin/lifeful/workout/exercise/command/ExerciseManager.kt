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
internal class ExerciseManager(
    private val repository: ExerciseRepository,
) : ExerciseAdd, ExerciseModify {
    override fun add(command: ExerciseCreateCommand): Exercise {
        checkDuplicateName(command.name)
        val exercise = Exercise(
            name = command.name,
            category = command.category,
            muscleGroups = command.muscleGroups,
        )
        return repository.save(exercise)
    }

    override fun modify(
        id: ExerciseId,
        command: ExerciseModifyCommand,
    ): Exercise {
        checkDuplicateName(command.name)
        val exercise = repository.findById(id.value)
            ?: throw ExerciseNotFoundException("운동 종목($id)을 찾을 수 없습니다.")
        exercise.modify(
            name = command.name,
            category = command.category,
            muscleGroups = command.muscleGroups,
        )
        return repository.save(exercise)
    }

    private fun checkDuplicateName(name: String) {
        repository.findByName(name)?.let {
            throw DuplicateException("중복된 운동 ${name}이(가) 존재합니다.")
        }
    }
}
