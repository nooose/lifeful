package lifeful.workout

import jakarta.validation.Valid
import lifeful.shared.id.ExerciseId
import lifeful.workout.command.ExerciseCommandService
import lifeful.workout.dto.request.ExerciseCreateRequest
import lifeful.workout.dto.request.ExerciseModifyRequest
import lifeful.workout.dto.response.ExerciseResponse
import lifeful.workout.query.ExerciseQueryService
import org.springframework.web.bind.annotation.*

@RestController
class ExerciseRestController(
    private val command: ExerciseCommandService,
    private val query: ExerciseQueryService,
) : ExerciseApi {
    @PostMapping("/api/v1/exercises")
    override fun addExercise(
        @RequestBody @Valid request: ExerciseCreateRequest,
    ) {
        command.add(request.toCommand())
    }

    override fun getExercise(id: ExerciseId): ExerciseResponse {
        return ExerciseResponse.from(query.byId(id))
    }

    @GetMapping("/api/v1/exercises")
    override fun getExercises(): List<ExerciseResponse> {
        return query.all().map { ExerciseResponse.from(it) }
    }

    @PutMapping("/api/v1/exercises/{exerciseId}")
    override fun modifyExercise(
        exerciseId: ExerciseId,
        request: ExerciseModifyRequest,
    ) {
        command.modify(exerciseId, request.toCommand())
    }
}
