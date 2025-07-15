package lifeful.workout.exercise

import lifeful.shared.id.ExerciseId
import org.springframework.data.jpa.repository.JpaRepository

internal interface ExerciseJpaRepository : JpaRepository<ExerciseEntity, ExerciseId> {
    fun findByName(name: String): ExerciseEntity?
}
