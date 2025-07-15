package lifeful.workout.routine

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table
import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.MemberId
import lifeful.shared.id.RoutineId

@Table(name = "routine")
@Entity
internal class RoutineEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: RoutineId,
    val memberId: MemberId,
    var name: String,
    items: List<RoutineItemEntity>,
    override val createdAt: Instant,
    override var modifiedAt: Instant,
) : BaseModel {
    @OneToMany(mappedBy = "routine", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("itemOrder ASC")
    val items: MutableList<RoutineItemEntity> = items.toMutableList()

    init {
        items.forEach { it.routine = this }
    }

    fun update(that: RoutineEntity) {
        this.items.clear()
        this.name = name
        this.items.addAll(that.items)
        this.modifiedAt = that.modifiedAt
    }

    fun toDomain(): Routine {
        return Routine(
            id = this.id,
            memberId = this.memberId,
            name = this.name,
            items = this.items.map { it.toDomain() },
            createdAt = this.createdAt,
            modifiedAt = this.modifiedAt,
        )
    }

    companion object {
        fun from(routine: Routine): RoutineEntity {
            return RoutineEntity(
                id = routine.id,
                memberId = routine.memberId,
                name = routine.name,
                items = routine.items.map {
                    RoutineItemEntity.of(it)
                },
                createdAt = routine.createdAt,
                modifiedAt = routine.modifiedAt,
            )
        }
    }
}
