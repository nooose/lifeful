package lifeful.member

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.support.RequiredAuthorization
import org.springframework.web.bind.annotation.GetMapping

@RequiredAuthorization
@Tag(
    name = "회원 API",
    description = "회원 관련 API"
)
internal interface MemberApi {
    @Operation(
        description = "회원 목록 조회",
    )
    @GetMapping("/api/members")
    fun getMembers(): List<Any>
}
