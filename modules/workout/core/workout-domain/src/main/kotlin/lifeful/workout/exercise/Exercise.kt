package lifeful.workout.exercise

import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.ExerciseId

data class Exercise(
    val name: String,
    val category: ExerciseCategory,
    val muscleGroups: Set<MuscleGroup>,
    val id: ExerciseId = ExerciseId(),
    override val createdAt: Instant = Instant.now(),
    override val modifiedAt: Instant = createdAt,
) : BaseModel {
    init {
        require(muscleGroups.isNotEmpty()) {
            MuscleGroupRequiredException("운동 부위를 하나 이상 선택해 주세요.")
        }
    }

    fun modify(
        name: String,
        category: ExerciseCategory,
        muscleGroups: Set<MuscleGroup>,
    ): Exercise {
        return copy(
            name = name,
            category = category,
            muscleGroups = muscleGroups,
            modifiedAt = Instant.now(),
        )
    }
}
