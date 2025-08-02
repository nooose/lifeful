package lifeful.event

import java.util.UUID

interface EventRecordRepository {
    fun findFailedEvents(): List<Event>

    fun findSuccessEvents(): List<Event>

    fun findFailedEvent(id: UUID): Event?
}
