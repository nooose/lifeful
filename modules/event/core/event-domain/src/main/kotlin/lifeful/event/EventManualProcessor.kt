package lifeful.event

import java.util.UUID

interface EventManualProcessor {
    fun publish(command: EventPublishCommand): Event

    fun resubmit(eventId: UUID)
}
