package lifeful.workout.dto.request

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import lifeful.shared.id.MemberId
import lifeful.workout.routine.command.RoutineCreateCommand
import lifeful.workout.routine.command.RoutineItemCreateCommand

@Schema(description = "루틴 생성 요청")
data class RoutineCreateRequest(
    @Schema(description = "루틴 이름", example = "지옥 루틴")
    @field:NotBlank
    val name: String,
    @Schema(description = "루틴에 포함될 운동 목록")
    val items: List<RoutineItemCreateRequest>
) {
    fun toCommand(loginMemberId: Long): RoutineCreateCommand {
        return RoutineCreateCommand(
            memberId = MemberId(loginMemberId),
            name = name,
            items = items.map {
                RoutineItemCreateCommand(
                    exerciseId = it.exerciseId,
                    itemOrder = it.order
                )
            }
        )
    }
}
