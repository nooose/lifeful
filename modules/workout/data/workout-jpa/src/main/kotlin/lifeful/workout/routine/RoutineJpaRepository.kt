package lifeful.workout.routine

import lifeful.shared.id.RoutineId
import org.springframework.data.jpa.repository.JpaRepository

interface RoutineJpaRepository : JpaRepository<RoutineEntity, RoutineId>
