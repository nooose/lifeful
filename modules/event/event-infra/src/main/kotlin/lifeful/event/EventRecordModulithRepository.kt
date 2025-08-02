package lifeful.event

import java.util.UUID
import org.springframework.modulith.events.core.EventPublicationRepository
import org.springframework.stereotype.Repository

@Repository
internal class EventRecordModulithRepository(
    private val eventPublicationRepository: EventPublicationRepository,
    private val mapper: EventObjectMapper,
) : EventRecordRepository {
    override fun findFailedEvents(): List<Event> {
        return eventPublicationRepository.findIncompletePublications()
            .map { mapper.toDomain(it) }
    }

    override fun findSuccessEvents(): List<Event> {
        return eventPublicationRepository.findCompletedPublications()
            .map { mapper.toDomain(it) }
    }

    override fun findFailedEvent(id: UUID): Event? {
        return eventPublicationRepository.findIncompletePublications()
            .firstOrNull { it.identifier == id }
            ?.let { mapper.toDomain(it) }
    }
}
