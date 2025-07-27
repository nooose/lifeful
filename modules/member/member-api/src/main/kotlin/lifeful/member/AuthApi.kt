package lifeful.member

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(
    name = "인증 API",
    description = "인증 관련 API",
)
interface AuthApi {
    @Operation(
        summary = "토큰 발급",
        description = "임시 기능",
    )
    fun getToken(request: TokenRequest): String
}
