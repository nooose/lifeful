package lifeful.workout.exercise

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import java.time.Instant
import lifeful.shared.id.ExerciseId

@Schema(description = "운동 종목 응답")
data class ExerciseResponse(
    @Schema(description = "운동 종목 ID")
    val id: ExerciseId,
    @Schema(description = "운동 종목 이름")
    @field:NotBlank
    val name: String,
    @Schema(description = "운동 종목 카테고리")
    val category: ExerciseCategory,
    @Schema(description = "운동 부위 목록")
    @field:NotEmpty
    val muscleGroups: Collection<MuscleGroup>,
    @Schema(description = "생성 시간")
    val createdAt: Instant,
    @Schema(description = "수정 시간")
    val modifiedAt: Instant,
) {
    companion object {
        fun from(exercise: Exercise): ExerciseResponse {
            return ExerciseResponse(
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
