package lifeful.event

import java.util.UUID
import org.springframework.stereotype.Service

@Service
internal class EventService(
    private val eventManualProcessor: EventManualProcessor,
    private val eventRecordRepository: EventRecordRepository,
) : EventProcessor, EventFinder {
    override fun publish(command: EventPublishCommand): EventRecord {
        return eventRecordRepository.save(command)
    }

    override fun resubmit(eventId: UUID) {
        eventManualProcessor.resubmit(eventId)
    }

    override fun getFailedEvents(): List<EventRecord> {
        return eventRecordRepository.findFailedEvents()
    }

    override fun getSuccessEvents(): List<EventRecord> {
        return eventRecordRepository.findSuccessEvents()
    }
}
