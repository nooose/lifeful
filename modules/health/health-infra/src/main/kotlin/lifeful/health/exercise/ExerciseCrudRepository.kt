package lifeful.health.exercise

import org.springframework.data.repository.CrudRepository

internal interface ExerciseCrudRepository : CrudRepository<ExerciseCache, Long>
