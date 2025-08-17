package lifeful.health.exercise

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import kotlin.time.DurationUnit
import kotlin.time.toDuration
import kotlin.time.toJavaDuration

/**
 * 운동 종목 Redis 캐시 저장소
 */
@Repository
internal class ExerciseRedisRepository(
    private val redisTemplate: RedisTemplate<String, Any>,
) : ExerciseCacheRepository {
    override fun putAll(exercises: List<Exercise>) {
        redisTemplate.delete(KEY)
        if (exercises.isEmpty()) return
        val caches = exercises.map { ExerciseCache.from(it) }
        redisTemplate.opsForValue()
            .set(KEY, caches, EXPIRE)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getAll(): List<Exercise> {
        val caches = redisTemplate.opsForValue().get(KEY) as? List<ExerciseCache> ?: emptyList()
        return caches.map { it.toDomain() }
    }

    override fun deleteAll() {
        redisTemplate.delete(KEY)
    }

    companion object {
        private const val KEY = "exercise::all"
        private val EXPIRE = 10.toDuration(DurationUnit.MINUTES).toJavaDuration()
    }
}
