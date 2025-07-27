package lifeful.health.workout

import lifeful.health.routine.Routine
import lifeful.health.routine.RoutineItem
import lifeful.shared.id.ExerciseId
import lifeful.shared.id.MemberId
import lifeful.shared.id.RoutineId

object WorkoutFixtures {
    fun workout(
        memberId: Long = 1L,
        routineId: Long = 1L,
        items: List<WorkoutItemRecord> = emptyList(),
    ): Workout =
        Workout(
            memberId = MemberId(memberId),
            routineId = RoutineId(routineId),
            items = items,
        )

    fun workoutItemRecord(
        exerciseId: ExerciseId = ExerciseId(1L),
        sets: List<SetRecord> = emptyList(),
        order: Int = 1,
    ): WorkoutItemRecord =
        WorkoutItemRecord(
            exerciseId = exerciseId,
            sets = sets,
            order = order,
        )

    fun routine(
        memberId: Long = 1L,
        name: String = "테스트 루틴",
        items: List<RoutineItem> = listOf(routineItem()),
    ): Routine =
        Routine(
            memberId = MemberId(memberId),
            name = name,
            items = items,
        )

    fun routineItem(
        exerciseId: ExerciseId = ExerciseId(1L),
        order: Int = 1,
    ) = RoutineItem(exerciseId, order)
}
