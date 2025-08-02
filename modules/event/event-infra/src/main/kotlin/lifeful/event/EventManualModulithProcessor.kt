package lifeful.event

import java.util.UUID
import org.springframework.modulith.events.IncompleteEventPublications
import org.springframework.modulith.events.core.EventPublicationRepository
import org.springframework.modulith.events.core.PublicationTargetIdentifier
import org.springframework.modulith.events.core.TargetEventPublication
import org.springframework.stereotype.Component

@Component
internal class EventManualModulithProcessor(
    private val publication: IncompleteEventPublications,
    private val eventPublicationRepository: EventPublicationRepository,
    private val mapper: EventObjectMapper,
) : EventManualProcessor {
    override fun publish(command: EventPublishCommand): Event {
        val event = TargetEventPublication.of(
            command.eventPayload,
            PublicationTargetIdentifier.of(command.listenerId),
        )
        val publishedTargetEvent = eventPublicationRepository.create(event)
        resubmit(publishedTargetEvent.identifier)
        return mapper.toDomain(publishedTargetEvent)
    }

    override fun resubmit(eventId: UUID) {
        publication.resubmitIncompletePublications {
            it.identifier == eventId
        }
    }
}
