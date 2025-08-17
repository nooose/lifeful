package lifeful.health.exercise

/**
 * 운동 종목 캐시 저장소
 */
interface ExerciseCacheRepository {
    fun putAll(exercises: List<Exercise>)

    fun getAll(): List<Exercise>

    fun deleteAll()
}
