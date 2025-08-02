package lifeful.event

interface EventRecordRepository {
    fun findFailedEvents(): List<EventRecord>

    fun findSuccessEvents(): List<EventRecord>
}
