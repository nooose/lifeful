package lifeful.health.routine.query

import lifeful.shared.id.RoutineId
import lifeful.health.routine.Routine

interface RoutineFinder {
    fun get(routineId: RoutineId): Routine

    fun find(routineId: RoutineId): Routine?
}
