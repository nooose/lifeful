package lifeful.admin

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import java.util.UUID
import lifeful.shared.RequiredAuthorization
import org.springdoc.core.annotations.ParameterObject

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
        @ParameterObject condition: EventSearchCondition
    ): List<EventResponse>

    @Operation(
        summary = "이벤트 재발행",
        description = "이벤트 재발행",
    )
    fun resubmit(eventId: UUID)
}
