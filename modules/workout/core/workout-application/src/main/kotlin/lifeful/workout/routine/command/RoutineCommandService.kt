package lifeful.workout.routine.command

import lifeful.shared.id.RoutineId
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
) {
    fun createRoutine(command: RoutineCreateCommand): Routine {
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

    fun modifyRoutine(id: RoutineId, command: RoutineModifyCommand): Routine {
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
}
