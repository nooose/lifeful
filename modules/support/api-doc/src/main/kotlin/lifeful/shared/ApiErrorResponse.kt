package lifeful.shared

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "API 에러 응답")
data class ApiErrorResponse(
    @Schema(description = "에러 발생 시각", example = "2024-01-01T12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val timestamp: LocalDateTime = LocalDateTime.now(),
    @Schema(description = "HTTP 상태 코드", example = "400")
    val status: Int,
    @Schema(description = "에러 타입", example = "BAD_REQUEST")
    val error: String,
    @Schema(description = "에러 메시지", example = "잘못된 요청입니다.")
    val message: String,
    @Schema(description = "요청 경로", example = "/api/v1/books/1/reviews")
    val path: String,
    val fields: Map<*, *> = emptyMap<Any, Any>(),
)
