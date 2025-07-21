package lifeful.health.workout

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.Instant
import lifeful.health.routine.Routine
import lifeful.shared.id.ExerciseId
import lifeful.shared.id.MemberId
import lifeful.shared.id.RoutineId
import lifeful.shared.model.BaseAggregateRootEntity

@Entity
@Table(name = "workouts")
class Workout(
    val memberId: MemberId,
    val routineId: RoutineId,
    items: List<WorkoutItemRecord> = emptyList(),
    val startedAt: Instant = Instant.now(),
    var finishedAt: Instant? = null,
    var memo: String? = null,
) : BaseAggregateRootEntity<Workout>() {
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id")
    val items: MutableList<WorkoutItemRecord> = items.toMutableList()

    fun addItem(exerciseId: ExerciseId) {
        val order = (this.items.maxOfOrNull { it.order } ?: 0) + 1
        this.items.add(
            WorkoutItemRecord(
                exerciseId = exerciseId,
                order = order,
            )
        )
    }

    fun finish(memo: String?) {
        require(items.isNotEmpty()) { "운동 기록 후 종료할 수 있습니다." }
        this.finishedAt = Instant.now()
        this.memo = memo
        registerEvent(WorkoutFinishedEvent(workoutId = this.id, memberId = memberId.value))
    }

    companion object {
        fun start(
            memberId: MemberId,
            routine: Routine,
        ): Workout {
            return Workout(
                memberId = memberId,
                routineId = RoutineId(routine.id),
            )
        }
    }
}
