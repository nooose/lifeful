package lifeful.member

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(
    name = "회원 API",
    description = "회원 관련 API",
)
interface MemberApi {
    @Operation(
        summary = "회원 가입",
        description = "임시 기능",
    )
    fun register(request: MemberRegisterRequest)
}
