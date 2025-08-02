package lifeful.event

/**
 * 이벤트 검색 유즈케이스
 */
interface EventFinder {
    fun getFailedEvents(): List<Event>

    fun getSuccessEvents(): List<Event>
}
