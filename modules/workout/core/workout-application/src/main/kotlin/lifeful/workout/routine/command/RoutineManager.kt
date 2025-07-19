package lifeful.workout.routine.command

import lifeful.shared.id.ExerciseId
import lifeful.shared.id.RoutineId
import lifeful.workout.exercise.query.ExerciseFinder
import lifeful.workout.routine.Routine
import lifeful.workout.routine.RoutineItem
import lifeful.workout.routine.RoutineRepository
import lifeful.workout.routine.query.RoutineFinder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
internal class RoutineManager(
    private val routineRepository: RoutineRepository,
    private val routineFinder: RoutineFinder,
    private val exerciseFinder: ExerciseFinder,
) : RoutineCreate, RoutineModify {
    override fun create(command: RoutineCreateCommand): Routine {
        validateExercise(command.items.map { it.exerciseId })
        val routine = Routine(
            memberId = command.memberId,
            name = command.name,
            items = command.items.map {
                RoutineItem(
                    exerciseId = it.exerciseId,
                    itemOrder = it.itemOrder,
                )
            },
        )
        return routineRepository.save(routine)
    }

    override fun modify(
        id: RoutineId,
        command: RoutineModifyCommand,
    ): Routine {
        validateExercise(command.items.map { it.exerciseId })
        val routine = routineFinder.get(id)
        val modifiedRoutine = routine.modify(
            name = command.name,
            items = command.items.map {
                RoutineItem(
                    exerciseId = it.exerciseId,
                    itemOrder = it.itemOrder,
                )
            },
        )
        return routineRepository.save(modifiedRoutine)
    }

    private fun validateExercise(ids: List<ExerciseId>) {
        val idToExercise = exerciseFinder.get(ids).associateBy { it.id }
        ids.forEach { exerciseId ->
            idToExercise[exerciseId.value] ?: throw IllegalArgumentException("유효하지 않은 운동 종목($exerciseId)입니다.")
        }
    }
}
