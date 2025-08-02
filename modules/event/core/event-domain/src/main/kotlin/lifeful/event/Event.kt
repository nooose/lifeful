package lifeful.event

import java.time.Instant
import java.util.UUID

data class Event(
    val uuid: UUID,
    val listenerId: String,
    val completedAt: Instant?,
    val publishedAt: Instant,
    val payload: Any,
) {

    val isCompleted: Boolean
        get() = completedAt != null
}
