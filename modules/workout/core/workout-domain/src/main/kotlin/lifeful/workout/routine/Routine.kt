package lifeful.workout.routine

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.MemberId

@Entity
class Routine(
    val memberId: MemberId,
    var name: String,
    items: List<RoutineItem>,
    override val createdAt: Instant = Instant.now(),
    override var modifiedAt: Instant = createdAt,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
) : BaseModel {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "routine_item",
        joinColumns = [JoinColumn(name = "routine_id")],
    )
    val items: MutableList<RoutineItem> = items.toMutableList()

    fun modify(
        name: String,
        items: List<RoutineItem>,
    ): Routine {
        this.items.addAll(items)
        this.name = name
        return this
    }
}
