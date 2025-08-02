package lifeful.event

import java.util.UUID

/**
 * 이벤트 처리 유즈케이스
 */
interface EventProcessor {
    fun publish(command: EventPublishCommand): Event

    fun resubmit(eventId: UUID)
}
