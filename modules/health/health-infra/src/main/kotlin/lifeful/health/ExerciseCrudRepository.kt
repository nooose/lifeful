package lifeful.health

import org.springframework.data.repository.CrudRepository

internal interface ExerciseCrudRepository : CrudRepository<ExerciseCache, Long>
