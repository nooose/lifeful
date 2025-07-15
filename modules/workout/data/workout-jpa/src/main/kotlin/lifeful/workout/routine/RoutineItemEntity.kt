package lifeful.workout.routine

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import lifeful.shared.id.ExerciseId

@Table(name = "routine_item")
@Entity
internal class RoutineItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val exerciseId: ExerciseId,
    val itemOrder: Int,
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    lateinit var routine: RoutineEntity

    fun toDomain(): RoutineItem {
        return RoutineItem(
            id = this.id,
            exerciseId = this.exerciseId,
            order = this.itemOrder,
        )
    }

    companion object {
        fun of(item: RoutineItem): RoutineItemEntity {
            return RoutineItemEntity(
                id = item.id,
                exerciseId = item.exerciseId,
                itemOrder = item.order,
            )
        }
    }
}
