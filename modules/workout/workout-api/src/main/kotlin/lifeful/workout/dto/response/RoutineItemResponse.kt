package lifeful.workout.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import lifeful.shared.id.ExerciseId
import lifeful.workout.routine.RoutineItem

@Schema(description = "루틴 항목 응답")
data class RoutineItemResponse(
    @Schema(description = "운동 종목 ID", example = "1")
    val exerciseId: ExerciseId,
    @Schema(description = "운동 순서", example = "1")
    val order: Int,
) {
    companion object {
        fun from(item: RoutineItem): RoutineItemResponse {
            return RoutineItemResponse(
                exerciseId = item.exerciseId,
                order = item.order,
            )
        }
    }
}
