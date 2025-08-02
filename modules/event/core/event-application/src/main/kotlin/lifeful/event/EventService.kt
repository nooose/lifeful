package lifeful.event

import java.util.UUID
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class EventService(
    private val eventManualProcessor: EventManualProcessor,
    private val eventRecordRepository: EventRecordRepository,
) : EventProcessor, EventFinder {
    @Transactional
    override fun publish(command: EventPublishCommand): Event {
        return eventManualProcessor.publish(command)
    }

    override fun resubmit(eventId: UUID) {
        val findFailedEvent = eventRecordRepository.findFailedEvent(eventId)
        requireNotNull(findFailedEvent) {
            "실패된 이벤트($eventId)를 찾을 수 없습니다."
        }
        eventManualProcessor.resubmit(eventId)
    }

    override fun getFailedEvents(): List<Event> {
        return eventRecordRepository.findFailedEvents()
    }

    override fun getSuccessEvents(): List<Event> {
        return eventRecordRepository.findSuccessEvents()
    }
}
