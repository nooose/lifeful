package lifeful.workout.command

import lifeful.shared.id.ExerciseId
import lifeful.workout.Exercise
import lifeful.workout.ExerciseNotFoundException
import lifeful.workout.ExerciseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class ExerciseCommandService(
    private val repository: ExerciseRepository,
) {
    fun add(command: ExerciseCreateCommand) {
        // TODO: 이름 중복 체크
        val exercise = Exercise(
            name = command.name,
            category = command.category,
            muscleGroups = command.muscleGroups,
        )
        repository.add(exercise)
    }

    fun modify(
        id: ExerciseId,
        command: ExerciseModifyCommand,
    ) {
        // TODO: 이름 중복 체크
        val exercise = repository.findById(id)
            ?: throw ExerciseNotFoundException("운동 종목($id)을 찾을 수 없습니다.")
        val modifiedExercise = exercise.modify(
            name = command.name,
            category = command.category,
            muscleGroups = command.muscleGroups,
        )
        repository.update(modifiedExercise)
    }
}
