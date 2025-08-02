package lifeful.event

import java.util.UUID
import org.springframework.modulith.events.IncompleteEventPublications
import org.springframework.modulith.events.core.EventPublicationRepository
import org.springframework.modulith.events.core.PublicationTargetIdentifier
import org.springframework.modulith.events.core.TargetEventPublication
import org.springframework.stereotype.Service

@Service
internal class EventService(
    private val publication: IncompleteEventPublications,
    private val eventPublicationRepository: EventPublicationRepository,
) : EventProcessor, EventFinder {
    override fun publish(command: EventPublishCommand): Event {
        val event = TargetEventPublication.of(
            command.payload,
            PublicationTargetIdentifier.of(command.listenerId),
        )
        val publishedEvent = eventPublicationRepository.create(event)
        return Event(
            uuid = publishedEvent.identifier,
            listenerId = publishedEvent.targetIdentifier.toString(),
            completedAt = publishedEvent.completionDate.orElse(null),
            publishedAt = publishedEvent.publicationDate,
            payload = publishedEvent.event,
            payloadType = publishedEvent.event.javaClass.name,
        )
    }

    override fun resubmit(eventId: UUID) {
        publication.resubmitIncompletePublications {
            it.identifier == eventId
        }
    }

    override fun getFailedEvents(): List<Event> {
        return eventPublicationRepository.findIncompletePublications()
            .map {
                Event(
                    uuid = it.identifier,
                    listenerId = it.targetIdentifier.toString(),
                    completedAt = it.completionDate.orElse(null),
                    publishedAt = it.publicationDate,
                    payload = it.event,
                    payloadType = it.event.javaClass.name,
                )
            }
    }

    override fun getSuccessEvents(): List<Event> {
        return eventPublicationRepository.findCompletedPublications()
            .map {
                Event(
                    uuid = it.identifier,
                    listenerId = it.targetIdentifier.toString(),
                    completedAt = it.completionDate.orElse(null),
                    publishedAt = it.publicationDate,
                    payload = it.event,
                    payloadType = it.event.javaClass.name,
                )
            }
    }
}
