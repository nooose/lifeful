package lifeful.health.routine.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import lifeful.shared.id.ExerciseId

@Schema(description = "루틴 항목 생성 요청")
data class RoutineItemCreateRequest(
    @Schema(description = "운동 종목 ID", example = "1")
    val exerciseId: ExerciseId,
    @Schema(description = "운동 순서", example = "1")
    val order: Int,
)
