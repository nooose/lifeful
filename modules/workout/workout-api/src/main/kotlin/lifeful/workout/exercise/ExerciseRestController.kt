package lifeful.workout.exercise

import jakarta.validation.Valid
import lifeful.shared.id.ExerciseId
import lifeful.workout.exercise.command.ExerciseAdd
import lifeful.workout.exercise.command.ExerciseModify
import lifeful.workout.exercise.query.ExerciseFinder
import org.springframework.web.bind.annotation.GetMapping
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
        exerciseId: ExerciseId,
        request: ExerciseModifyRequest,
    ) {
        modifyExercise.modify(exerciseId, request.toCommand())
    }
}
