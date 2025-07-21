package lifeful.health.routine.command

import lifeful.shared.id.RoutineId
import lifeful.health.routine.Routine

/**
 * 운동 루틴 수정 유즈케이스
 */
interface RoutineModify {
    fun modify(
        id: RoutineId,
        command: RoutineModifyCommand,
    ): Routine
}
