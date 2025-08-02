package lifeful.health.exercise

import jakarta.validation.Valid
import lifeful.health.exercise.command.ExerciseAdd
import lifeful.health.exercise.command.ExerciseModify
import lifeful.health.exercise.query.ExerciseFinder
import lifeful.shared.id.ExerciseId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ExerciseRestController(
    private val addExercise: ExerciseAdd,
    private val modifyExercise: ExerciseModify,
    private val finder: ExerciseFinder,
) : ExerciseApi {
    @PostMapping("/api/v1/exercises")
    override fun addExercise(
        @RequestBody @Valid request: ExerciseCreateRequest,
    ) {
        addExercise.add(request.toCommand())
    }

    override fun getExercise(id: ExerciseId): ExerciseResponse {
        return ExerciseResponse.from(finder.get(id))
    }

    @GetMapping("/api/v1/exercises")
    override fun getExercises(): List<ExerciseResponse> {
        return finder.all().map { ExerciseResponse.from(it) }
    }

    @PutMapping("/api/v1/exercises/{exerciseId}")
    override fun modifyExercise(
        @PathVariable exerciseId: ExerciseId,
        @RequestBody @Valid request: ExerciseModifyRequest,
    ) {
        modifyExercise.modify(exerciseId, request.toCommand())
    }
}
