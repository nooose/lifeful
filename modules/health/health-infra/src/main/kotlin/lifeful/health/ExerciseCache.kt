package lifeful.health

import java.time.Instant
import lifeful.health.exercise.Exercise
import lifeful.health.exercise.ExerciseCategory
import lifeful.health.exercise.MuscleGroup
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "exercise", timeToLive = 600)
internal data class ExerciseCache(
    @Id
    val id: Long,
    val name: String,
    val category: ExerciseCategory,
    val muscleGroups: Set<MuscleGroup>,
    val createdAt: Instant,
    val modifiedAt: Instant,
) {
    fun toDomain(): Exercise {
        return Exercise(
            id = id,
            name = name,
            category = category,
            muscleGroups = muscleGroups,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
    }

    companion object {
        fun from(exercise: Exercise): ExerciseCache {
            return ExerciseCache(
                id = exercise.id,
                name = exercise.name,
                category = exercise.category,
                muscleGroups = exercise.muscleGroups,
                createdAt = exercise.createdAt,
                modifiedAt = exercise.modifiedAt,
            )
        }
    }
}
