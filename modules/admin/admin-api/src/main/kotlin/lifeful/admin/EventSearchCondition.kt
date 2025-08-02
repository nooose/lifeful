package lifeful.admin

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "이벤트 검색 조건")
data class EventSearchCondition(
    @field:Schema(description = "이벤트 결과 타입")
    val resultType: EventType,
) {
    enum class EventType(
        val description: String,
    ) {
        COMPLETED("완료된 이벤트"),
        FAILED("실패한 이벤트"),
    }
}
