package lifeful.workout.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import lifeful.workout.exercise.ExerciseCategory
import lifeful.workout.exercise.MuscleGroup
import lifeful.workout.exercise.command.ExerciseModifyCommand

@Schema(description = "운동 종목 수정 요청")
data class ExerciseModifyRequest(
    @Schema(description = "운동 종목 이름")
    @field:NotBlank
    val name: String,
    @Schema(description = "운동 종목 카테고리")
    val category: ExerciseCategory,
    @Schema(description = "운동 부위 목록")
    @field:NotEmpty
    val muscleGroups: Set<MuscleGroup>,
) {
    fun toCommand(): ExerciseModifyCommand {
        return ExerciseModifyCommand(
            name = name,
            category = category,
            muscleGroups = muscleGroups,
        )
    }
}
