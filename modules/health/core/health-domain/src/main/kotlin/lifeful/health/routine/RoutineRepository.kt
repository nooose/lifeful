package lifeful.health.routine

import org.springframework.data.repository.Repository

interface RoutineRepository : Repository<Routine, Long> {
    fun save(routine: Routine): Routine

    fun findById(id: Long): Routine?
}
