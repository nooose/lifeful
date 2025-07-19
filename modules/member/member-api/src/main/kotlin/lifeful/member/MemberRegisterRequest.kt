package lifeful.member

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "회원 가입 요청")
data class MemberRegisterRequest(
    @field:Schema(description = "사용자 이름")
    @field:NotBlank
    val nickname: String,
    @field:Schema(description = "이메일 주소")
    @field:Email
    val email: String,
    @field:NotBlank
    val password: String,
) {
    fun toCommand(): MemberRegisterCommand {
        return MemberRegisterCommand(
            nickname = nickname,
            email = email,
            password = password,
        )
    }
}
