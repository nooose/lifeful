package lifeful.member

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "토큰 발급 요청")
data class TokenRequest(
    @field:Schema(description = "사용자 식별자")
    val memberId: Long,
)
