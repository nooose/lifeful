package lifeful.health

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import io.kotest.matchers.shouldBe
import lifeful.base.IntegrationTest
import lifeful.health.exercise.ExerciseCategory
import lifeful.health.exercise.MuscleGroup
import lifeful.health.exercise.command.ExerciseAdd
import lifeful.health.exercise.command.ExerciseCreateCommand
import lifeful.health.routine.command.RoutineCreate
import lifeful.health.routine.command.RoutineCreateCommand
import lifeful.health.routine.command.RoutineItemCreateCommand
import lifeful.health.routine.query.RoutineFinder
import lifeful.shared.id.ExerciseId
import lifeful.shared.id.MemberId
import lifeful.shared.id.RoutineId
import org.springframework.context.annotation.Import

@Import(WorkoutTestConfiguration::class)
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
