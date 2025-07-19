package lifeful.member

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email

@Schema(description = "회원 가입 요청")
data class MemberRegisterRequest(
    @field:Schema(description = "사용자 이름")
    val nickname: String,
    @field:Schema(description = "이메일 주소")
    @field:Email
    val email: String,
) {
    fun toCommand(): MemberRegisterCommand {
        return MemberRegisterCommand(
            nickname = nickname,
            email = email,
        )
    }
}
