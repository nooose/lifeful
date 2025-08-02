package lifeful.admin

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.Valid
import java.util.UUID
import lifeful.admin.EventSearchCondition.EventType.COMPLETED
import lifeful.admin.EventSearchCondition.EventType.FAILED
import lifeful.event.EventFinder
import lifeful.event.EventProcessor
import lifeful.event.EventPublishCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
class EventRestController(
    private val eventFinder: EventFinder,
    private val eventProcessor: EventProcessor,
    private val objectMapper: ObjectMapper,
) : EventApi {
    @PostMapping("/api/v1/admin/events/{eventId}:resubmit")
    override fun resubmit(
        @PathVariable eventId: UUID,
    ) {
        eventProcessor.resubmit(eventId)
    }

    @GetMapping("/api/v1/admin/events")
    override fun getEvents(
        @ModelAttribute condition: EventSearchCondition,
    ): List<EventResponse> {
        val events = when (condition.resultType) {
            COMPLETED -> eventFinder.getSuccessEvents()
            FAILED -> eventFinder.getFailedEvents()
        }
        return events.map { EventResponse.from(it) }
    }

    @PostMapping("/api/v1/admin/events")
    override fun publishEvent(
        @RequestBody @Valid request: EventPublishRequest,
    ): ResponseEntity<Unit> {
        val command = EventPublishCommand(
            payload = convertPayload(request.payloadType, request.payload),
            listenerId = request.listenerId,
        )

        val event = eventProcessor.publish(command)
        val uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/${event.uuid}")
            .build()
            .toUri()
        return ResponseEntity.created(uri).build()
    }

    private fun convertPayload(
        className: String,
        payload: Any,
    ): Any {
        val clazz = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            error("$className 이벤트 타입은 존재하지 않습니다.")
        }
        return objectMapper.convertValue(payload, clazz)
    }
}
