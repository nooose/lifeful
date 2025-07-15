package lifeful.workout.dto.response

import io.swagger.v3.oas.annotations.media.Schema
import lifeful.shared.id.RoutineId
import lifeful.workout.routine.Routine

@Schema(description = "루틴 응답")
data class RoutineResponse(
    @Schema(description = "루틴 ID", example = "1")
    val id: RoutineId,
    @Schema(description = "루틴 이름", example = "지옥 루틴")
    val name: String,
    @Schema(description = "루틴에 포함된 운동 목록")
    val items: List<RoutineItemResponse>,
) {
    companion object {
        fun from(routine: Routine): RoutineResponse {
            return RoutineResponse(
                id = routine.id,
                name = routine.name,
                items = routine.items.map {
                    RoutineItemResponse.from(it)
                },
            )
        }
    }
}
