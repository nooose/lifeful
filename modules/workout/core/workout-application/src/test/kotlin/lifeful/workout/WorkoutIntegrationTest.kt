package lifeful.workout

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import lifeful.base.IntegrationTest
import lifeful.shared.id.ExerciseId
import lifeful.shared.id.MemberId
import lifeful.shared.id.RoutineId
import lifeful.workout.exercise.ExerciseCategory
import lifeful.workout.exercise.MuscleGroup
import lifeful.workout.exercise.command.ExerciseAdd
import lifeful.workout.exercise.command.ExerciseCreateCommand
import lifeful.workout.routine.command.RoutineCreate
import lifeful.workout.routine.command.RoutineCreateCommand
import lifeful.workout.routine.command.RoutineItemCreateCommand
import lifeful.workout.routine.query.RoutineFinder

@IntegrationTest
class WorkoutIntegrationTest(
    private val exerciseAdd: ExerciseAdd,
    private val routineCreate: RoutineCreate,
    private val routineFinder: RoutineFinder,
) : BehaviorSpec({
    extension(SpringTestExtension(SpringTestLifecycleMode.Root))

    Given("운동 종목을 추가하고") {
        val exercise = exerciseAdd.add(
            command = ExerciseCreateCommand(
                name = "벤치프레스",
                category = ExerciseCategory.STRENGTH,
                muscleGroups = setOf(MuscleGroup.CHEST),
            ),
        )
        When("루틴을 저장하면") {
            val routine = routineCreate.create(
                command = RoutineCreateCommand(
                    memberId = MemberId(1),
                    name = "나만의 루틴",
                    items = listOf(
                        RoutineItemCreateCommand(
                            exerciseId = ExerciseId(exercise.id),
                            itemOrder = 1,
                        ),
                    ),
                ),
            )
            Then("루틴 목록을 확인할 수 있다.") {
                val findRoutine = routineFinder.get(RoutineId(routine.id))
                routine.id shouldBe findRoutine.id
                routine.name shouldBe findRoutine.name
            }
        }
    }

    Given("운동 종목을 추가하지 않고") {
        val invalidExerciseId = ExerciseId(Long.MAX_VALUE)
        When("루틴을 저장하면") {
            val command = RoutineCreateCommand(
                memberId = MemberId(1),
                name = "나만의 루틴",
                items = listOf(
                    RoutineItemCreateCommand(
                        exerciseId = invalidExerciseId,
                        itemOrder = 1,
                    ),
                ),
            )
            Then("루틴 목록을 확인할 수 있다.") {
                shouldThrow<IllegalArgumentException> {
                    routineCreate.create(command)
                }.apply {
                    println(this.message)
                }
            }
        }
    }
})
