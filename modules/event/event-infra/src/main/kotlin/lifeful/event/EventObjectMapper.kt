package lifeful.event

import org.springframework.modulith.events.core.TargetEventPublication
import org.springframework.stereotype.Component

@Component
internal class EventObjectMapper {
    fun toDomain(targetEvent: TargetEventPublication): Event {
        return Event(
            uuid = targetEvent.identifier,
            listenerId = targetEvent.targetIdentifier.toString(),
            completedAt = targetEvent.completionDate.orElse(null),
            publishedAt = targetEvent.publicationDate,
            payload = targetEvent.event,
            payloadType = targetEvent.event.javaClass.name,
        )
    }
}
