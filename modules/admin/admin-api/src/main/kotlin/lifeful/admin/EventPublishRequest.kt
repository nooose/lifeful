package lifeful.admin

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "이벤트 수동 발행 요청")
data class EventPublishRequest(
    @Schema(description = "이벤트 페이로드 타입", example = "lifeful.member.SampleEvent")
    val payloadType: String,
    @Schema(description = "이벤트 페이로드")
    val payload: Any,
    @Schema(description = "이벤트 리스너 ID")
    @field:NotBlank
    val listenerId: String,
)
