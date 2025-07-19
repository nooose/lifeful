package lifeful.workout.routine.query

import lifeful.shared.id.RoutineId
import lifeful.workout.routine.Routine

interface RoutineFinder {
    fun get(routineId: RoutineId): Routine
    fun find(routineId: RoutineId): Routine?
}