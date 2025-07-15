package lifeful.workout.exercise

import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.shared.id.ExerciseId
import lifeful.support.RequiredAuthorization
import lifeful.workout.exercise.ExerciseResponse

@RequiredAuthorization
@Tag(
    name = "운동 종목 API",
    description = "운동 종목 관련 API",
)
internal interface ExerciseApi {
    fun addExercise(request: ExerciseCreateRequest)

    fun getExercise(id: ExerciseId): ExerciseResponse

    fun getExercises(): List<ExerciseResponse>

    fun modifyExercise(
        exerciseId: ExerciseId,
        request: ExerciseModifyRequest,
    )
}
