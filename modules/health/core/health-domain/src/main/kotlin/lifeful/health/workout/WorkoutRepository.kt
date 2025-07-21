package lifeful.health.workout

import org.springframework.data.repository.Repository

/**
 * 운동 기록 저장소
 */
interface WorkoutRepository : Repository<Workout, Long> {
    fun save(workout: Workout): Workout
    fun findById(id: Long): Workout?
}
