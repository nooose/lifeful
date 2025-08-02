package lifeful.event

/**
 * 이벤트 검색 유즈케이스
 */
interface EventFinder {
    fun getFailedEvents(): List<EventRecord>

    fun getSuccessEvents(): List<EventRecord>
}
