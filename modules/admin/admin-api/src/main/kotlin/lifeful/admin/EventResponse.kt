package lifeful.admin

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant
import java.util.UUID
import lifeful.event.EventRecord

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "이벤트 응답")
data class EventResponse(
    @Schema(description = "이벤트 ID")
    val uuid: UUID,
    @Schema(description = "이벤트 리스너 ID")
    val listenerId: String,
    @Schema(description = "완료된 시간")
    val completedAt: Instant?,
    @Schema(description = "발행된 시간")
    val publishedAt: Instant,
    @Schema(description = "이벤트 페이로드")
    val payloadType: String,
    @Schema(description = "이벤트 페이로드 타입")
    val payload: Any,
) {
    companion object {
        fun from(event: EventRecord): EventResponse {
            return EventResponse(
                uuid = event.uuid,
                listenerId = event.listenerId,
                completedAt = event.completedAt,
                publishedAt = event.publishedAt,
                payload = event.payload,
                payloadType = event.payloadType,
            )
        }
    }
}
