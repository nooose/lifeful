package lifeful.workout.routine

import lifeful.shared.id.RoutineId
import lifeful.workout.routine.RoutineEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class RoutineRepositoryImpl(
    private val routineJpaRepository: RoutineJpaRepository,
) : RoutineRepository {
    override fun save(routine: Routine): Routine {
        val entity = RoutineEntity.from(routine)
        return routineJpaRepository.save(entity).toDomain()
    }

    override fun findById(id: RoutineId): Routine? {
        return routineJpaRepository.findByIdOrNull(id.value)?.toDomain()
    }
}
