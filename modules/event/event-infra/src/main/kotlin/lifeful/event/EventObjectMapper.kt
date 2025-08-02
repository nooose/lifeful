package lifeful.event

import org.springframework.modulith.events.core.TargetEventPublication
import org.springframework.stereotype.Component

@Component
internal class EventObjectMapper {
    fun toDomain(targetEvent: TargetEventPublication): EventRecord {
        return EventRecord(
            uuid = targetEvent.identifier,
            listenerId = targetEvent.targetIdentifier.toString(),
            completedAt = targetEvent.completionDate.orElse(null),
            publishedAt = targetEvent.publicationDate,
            payload = targetEvent.event,
            payloadType = targetEvent.event.javaClass.name,
        )
    }
}
