package lifeful.health.exercise

interface ExerciseCacheRepository {
    fun putAll(exercises: List<Exercise>)

    fun getAll(): List<Exercise>

    fun deleteAll()
}
