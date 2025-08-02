package lifeful.event

import org.springframework.modulith.events.core.EventPublicationRepository
import org.springframework.stereotype.Repository

@Repository
internal class EventRecordModulithRepository(
    private val eventPublicationRepository: EventPublicationRepository,
    private val mapper: EventObjectMapper,
) : EventRecordRepository {
    override fun findFailedEvents(): List<EventRecord> {
        return eventPublicationRepository.findIncompletePublications()
            .map { mapper.toDomain(it) }
    }

    override fun findSuccessEvents(): List<EventRecord> {
        return eventPublicationRepository.findCompletedPublications()
            .map { mapper.toDomain(it) }
    }
}
