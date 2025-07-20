package lifeful.member

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Email
import lifeful.member.command.MemberLoginCommand

@Schema(description = "토큰 발급 요청")
data class TokenRequest(
    @field:Schema(description = "사용자 이메일")
    @field:Email
    val email: String,
    @field:Schema(description = "사용자 비밀번호")
    @field:NotBlank
    val password: String,
) {
    fun toCommand(): MemberLoginCommand {
        return MemberLoginCommand(email, password)
    }
}
