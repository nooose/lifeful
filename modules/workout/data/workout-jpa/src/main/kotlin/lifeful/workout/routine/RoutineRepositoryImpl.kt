package lifeful.workout.routine

import lifeful.shared.id.RoutineId
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class RoutineRepositoryImpl(
    private val routineJpaRepository: RoutineJpaRepository,
) : RoutineRepository {
    override fun save(routine: Routine): Routine {
        val entity = RoutineEntity.from(routine)
        return routineJpaRepository.save(entity).toDomain()
    }

    override fun update(routine: Routine): Routine {
        val existsRoutine = routineJpaRepository.findByIdOrNull(routine.id)
            ?: throw RoutineNotFoundException("운동 종목(${routine.id}을 찾을 수 없습니다.")
        existsRoutine.update(RoutineEntity.from(routine))
        return existsRoutine.toDomain()
    }

    override fun findById(id: RoutineId): Routine? {
        return routineJpaRepository.findByIdOrNull(id)?.toDomain()
    }
}
