package lifeful.workout.routine.command

import lifeful.shared.id.ExerciseId
import lifeful.shared.id.RoutineId
import lifeful.workout.exercise.query.ExerciseLookupService
import lifeful.workout.routine.Routine
import lifeful.workout.routine.RoutineItem
import lifeful.workout.routine.RoutineRepository
import lifeful.workout.routine.query.RoutineLookupService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class RoutineCommandService(
    private val routineRepository: RoutineRepository,
    private val routineLookupService: RoutineLookupService,
    private val exerciseLookupService: ExerciseLookupService,
) {
    fun createRoutine(command: RoutineCreateCommand): Routine {
        validateExercise(command.items.map { it.exerciseId })
        val routine = Routine(
            memberId = command.memberId,
            name = command.name,
            items = command.items.map {
                RoutineItem(
                    exerciseId = it.exerciseId,
                    order = it.itemOrder,
                )
            },
        )
        return routineRepository.save(routine)
    }

    fun modifyRoutine(
        id: RoutineId,
        command: RoutineModifyCommand,
    ): Routine {
        validateExercise(command.items.map { it.exerciseId })
        val routine = routineLookupService.getRoutine(id)
        val modifiedRoutine = routine.modify(
            name = command.name,
            items = command.items.map {
                RoutineItem(
                    exerciseId = it.exerciseId,
                    order = it.itemOrder,
                )
            },
        )
        return routineRepository.update(modifiedRoutine)
    }

    private fun validateExercise(ids: List<ExerciseId>) {
        val idToExercise = exerciseLookupService.byIds(ids).associateBy { it.id }
        ids.forEach { exerciseId ->
            idToExercise[exerciseId] ?: throw IllegalArgumentException("유효하지 않은 운동 종목($exerciseId)입니다.")
        }
    }
}
