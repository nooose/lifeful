package lifeful.health.exercise.command

import lifeful.health.exercise.Exercise
import lifeful.health.exercise.ExerciseCacheRepository
import lifeful.health.exercise.ExerciseNotFoundException
import lifeful.health.exercise.ExerciseRepository
import lifeful.shared.exception.DuplicateException
import lifeful.shared.id.ExerciseId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
internal class ExerciseManager(
    private val repository: ExerciseRepository,
    private val cacheRepository: ExerciseCacheRepository,
) : ExerciseAdd, ExerciseModify {
    override fun add(command: ExerciseCreateCommand): Exercise {
        checkDuplicateName(command.name)
        val exercise = Exercise(
            name = command.name,
            category = command.category,
            muscleGroups = command.muscleGroups,
        )
        repository.save(exercise)

        cacheRepository.deleteAll()
        return exercise
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

        repository.save(exercise)

        cacheRepository.deleteAll()
        return exercise
    }

    private fun checkDuplicateName(name: String) {
        repository.findByName(name)?.let {
            throw DuplicateException("중복된 운동 ${name}이(가) 존재합니다.")
        }
    }
}
