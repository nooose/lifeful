package lifeful.health.routine

import jakarta.persistence.CollectionTable
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import lifeful.shared.id.MemberId
import lifeful.shared.model.BaseAggregateRootEntity

@Entity
class Routine(
    val memberId: MemberId,
    var name: String,
    items: List<RoutineItem>,
) : BaseAggregateRootEntity<Routine>() {
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
