package lifeful.workout.routine.query

import lifeful.shared.id.RoutineId
import lifeful.workout.routine.Routine
import lifeful.workout.routine.RoutineNotFoundException
import lifeful.workout.routine.RoutineRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
internal class RoutineQueryService(
    private val routineRepository: RoutineRepository,
) : RoutineFinder {
    override fun get(routineId: RoutineId): Routine {
        return routineRepository.findById(id = routineId.value)
            ?: throw RoutineNotFoundException("운동 루틴($routineId)을 찾을 수 없습니다.")
    }

    override fun find(routineId: RoutineId): Routine? {
        return routineRepository.findById(id = routineId.value)
    }
}
