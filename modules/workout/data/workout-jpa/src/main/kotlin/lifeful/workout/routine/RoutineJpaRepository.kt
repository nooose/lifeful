package lifeful.workout.routine

import org.springframework.data.jpa.repository.JpaRepository

internal interface RoutineJpaRepository : JpaRepository<RoutineEntity, Long>
