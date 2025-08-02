package lifeful.event

import java.util.UUID

interface EventManualProcessor {
    fun resubmit(eventId: UUID)

    fun publish(command: EventPublishCommand): EventRecord
}
