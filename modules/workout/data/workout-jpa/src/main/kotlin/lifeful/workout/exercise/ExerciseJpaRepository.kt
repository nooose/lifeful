package lifeful.workout.exercise

import org.springframework.data.jpa.repository.JpaRepository

internal interface ExerciseJpaRepository : JpaRepository<ExerciseEntity, Long> {
    fun findByName(name: String): ExerciseEntity?
}
