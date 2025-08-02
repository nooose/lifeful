package lifeful.event

interface EventFinder {
    fun getFailedEvents(): List<Event>
    fun getSuccessEvents(): List<Event>
}
