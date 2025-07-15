package lifeful.workout.routine

import jakarta.persistence.*
import lifeful.shared.id.ExerciseId

@Table(name = "routine_item")
@Entity
class RoutineItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val exerciseId: ExerciseId,
    val order: Int,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    lateinit var routine: RoutineEntity

    fun toDomain(): RoutineItem {
        return RoutineItem(
            id = this.id,
            exerciseId = this.exerciseId,
            order = this.order
        )
    }

    companion object {
        fun of(item: RoutineItem): RoutineItemEntity {
            return RoutineItemEntity(
                id = item.id,
                exerciseId = item.exerciseId,
                order = item.order,
            )
        }
    }
}
