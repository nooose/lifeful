package lifeful.health

import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import lifeful.health.exercise.ExerciseRestController
import lifeful.health.exercise.command.ExerciseAdd
import lifeful.health.exercise.command.ExerciseModify
import lifeful.health.exercise.query.ExerciseFinder
import org.assertj.core.api.Assertions
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.assertj.MockMvcTester

@WebMvcTest(controllers = [ExerciseRestController::class])
class ExerciseRestControllerTest(
    private val mockMvcTester: MockMvcTester,
    @MockkBean
    private val addExercise: ExerciseAdd,
    @MockkBean
    private val modifyExercise: ExerciseModify,
    @MockkBean
    private val finder: ExerciseFinder,
) : StringSpec({

    "운동 종목 추가 API 테스트" {
        every { finder.all() } returns emptyList()

        Assertions.assertThat(
            mockMvcTester.get()
                .uri("/api/v1/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
        ).hasStatusOk()
    }
})
