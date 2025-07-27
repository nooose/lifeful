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
    )
    fun register(request: MemberRegisterRequest)

    @Operation(
        summary = "회원 비활성화",
    )
    fun deactivate(memberId: Long)

    @Operation(
        summary = "회원 목록 조회",
    )
    fun findAll(): List<MemberResponse>
}
