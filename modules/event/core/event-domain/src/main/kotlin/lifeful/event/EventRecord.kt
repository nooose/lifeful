package lifeful.event

import java.time.Instant
import java.util.UUID

data class EventRecord(
    val uuid: UUID,
    val listenerId: String,
    val completedAt: Instant?,
    val publishedAt: Instant,
    val payload: Any,
    val payloadType: String,
) {
    val isCompleted: Boolean
        get() = completedAt != null
}
