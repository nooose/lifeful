package lifeful.workout.routine

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.support.RequiredAuthorization
import lifeful.workout.routine.dto.request.RoutineCreateRequest
import lifeful.workout.routine.dto.request.RoutineModifyRequest
import lifeful.workout.routine.dto.response.RoutineResponse

@RequiredAuthorization
@Tag(
    name = "운동 루틴 API",
    description = "운동 루틴 관련 API",
)
interface RoutineApi {
    @Operation(
        summary = "루틴 생성",
        description = "새로운 운동 루틴을 생성합니다.",
    )
    fun createRoutine(
        request: RoutineCreateRequest,
        loginMemberId: Long,
    )

    @Operation(
        summary = "루틴 수정",
        description = "기존 운동 루틴을 수정합니다.",
    )
    fun modifyRoutine(
        routineId: Long,
        request: RoutineModifyRequest,
        loginMemberId: Long,
    )

    @Operation(
        summary = "루틴 조회",
        description = "특정 운동 루틴의 정보를 조회합니다.",
    )
    fun getRoutine(
        routineId: Long,
        loginMemberId: Long,
    ): RoutineResponse
}
