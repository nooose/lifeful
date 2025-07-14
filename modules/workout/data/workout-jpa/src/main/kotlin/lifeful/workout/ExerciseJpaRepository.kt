package lifeful.workout

import lifeful.shared.id.ExerciseId
import org.springframework.data.jpa.repository.JpaRepository

interface ExerciseJpaRepository : JpaRepository<ExerciseEntity, ExerciseId>
