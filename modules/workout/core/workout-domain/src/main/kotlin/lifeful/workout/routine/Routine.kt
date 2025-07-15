package lifeful.workout.routine

import java.time.Instant
import lifeful.shared.BaseModel
import lifeful.shared.id.MemberId
import lifeful.shared.id.RoutineId

data class Routine(
    val memberId: MemberId,
    val name: String,
    val items: List<RoutineItem>,
    override val createdAt: Instant = Instant.now(),
    override val modifiedAt: Instant = createdAt,
    val id: RoutineId = RoutineId(),
) : BaseModel {
    fun modify(
        name: String,
        items: List<RoutineItem>,
    ): Routine {
        return copy(
            name = name,
            items = items,
            modifiedAt = Instant.now(),
        )
    }
}
