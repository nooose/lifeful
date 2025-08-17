package lifeful.health.exercise

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.shared.RequiredAuthorization
import lifeful.shared.id.ExerciseId

@RequiredAuthorization
@Tag(
    name = "운동 종목 API",
    description = "운동 종목 관련 API",
)
internal interface ExerciseApi {
    @Operation(
        summary = "운동 종목 등록",
    )
    fun addExercise(request: ExerciseCreateRequest)

    @Operation(
        summary = "운동 종목 상세 조회",
    )
    fun getExercise(id: ExerciseId): ExerciseResponse

    @Operation(
        summary = "운동 종목 목록 조회",
    )
    fun getExercises(): List<ExerciseResponse>

    @Operation(
        summary = "운동 종목 정보 수정",
    )
    fun modifyExercise(
        exerciseId: ExerciseId,
        request: ExerciseModifyRequest,
    )
}
