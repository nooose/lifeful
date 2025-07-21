package lifeful.health.routine.command

import lifeful.health.routine.Routine

/**
 * 운동 루틴 생성 유즈케이스
 */
interface RoutineCreate {
    fun create(command: RoutineCreateCommand): Routine
}
