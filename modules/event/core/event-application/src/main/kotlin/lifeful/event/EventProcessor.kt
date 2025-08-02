package lifeful.event

import java.util.UUID

interface EventProcessor {
    fun resubmit(eventId: UUID)
}
