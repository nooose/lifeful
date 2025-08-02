package lifeful.admin

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import lifeful.shared.RequiredAuthorization
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.ResponseEntity

@RequiredAuthorization
@Tag(
    name = "이벤트 API",
    description = "이벤트 관련 API",
)
interface EventApi {
    @Operation(
        summary = "이벤트 목록 조회",
    )
    fun getEvents(
        @ParameterObject condition: EventSearchCondition,
    ): List<EventResponse>

    @Operation(
        summary = "이벤트 수동 발행",
    )
    fun publishEvent(request: EventPublishRequest): ResponseEntity<Unit>

    @Operation(
        summary = "이벤트 재처리",
        description = "미처리되었거나 처리에 실패한 이벤트를 다시 처리합니다.",
    )
    fun resubmit(eventId: UUID)
}
