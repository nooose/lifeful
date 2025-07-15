package lifeful.workout.routine.query

import lifeful.shared.id.RoutineId
import lifeful.workout.routine.Routine
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class RoutineQueryService(
    private val routineLookupService: RoutineLookupService,
) {
    fun getRoutine(routineId: RoutineId): Routine {
        // TODO: 인가 적용
        return routineLookupService.getRoutine(routineId)
    }
}
