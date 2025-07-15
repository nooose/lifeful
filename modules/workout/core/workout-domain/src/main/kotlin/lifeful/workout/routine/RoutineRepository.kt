package lifeful.workout.routine

import lifeful.shared.id.RoutineId

interface RoutineRepository {
    fun save(routine: Routine): Routine
    fun update(routine: Routine): Routine
    fun findById(id: RoutineId): Routine?
}
