package lifeful.member

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import lifeful.member.command.MemberChangeNicknameCommand
import lifeful.shared.id.MemberId

@Schema(description = "회원 닉네임 변경 요청")
data class MemberChangeNicknameRequest(
    @Schema(description = "새로운 닉네임", example = "새로운닉네임")
    @field:NotBlank
    val newNickname: String,
) {
    fun toCommand(memberId: Long): MemberChangeNicknameCommand {
        return MemberChangeNicknameCommand(
            memberId = MemberId(memberId),
            newNickname = newNickname,
        )
    }
}
