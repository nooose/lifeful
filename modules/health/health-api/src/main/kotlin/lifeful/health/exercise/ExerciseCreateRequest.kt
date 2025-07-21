package lifeful.health.exercise

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import lifeful.health.exercise.command.ExerciseCreateCommand

@Schema(description = "운동 종목 추가 요청")
data class ExerciseCreateRequest(
    @Schema(description = "운동 종목 이름")
    @field:NotBlank
    val name: String,
    @Schema(description = "운동 종목 카테고리")
    val category: ExerciseCategory,
    @Schema(description = "운동 부위 목록")
    @field:NotEmpty
    val muscleGroups: Set<MuscleGroup>,
) {
    fun toCommand(): ExerciseCreateCommand {
        return ExerciseCreateCommand(
            name = name,
            category = category,
            muscleGroups = muscleGroups,
        )
    }
}
