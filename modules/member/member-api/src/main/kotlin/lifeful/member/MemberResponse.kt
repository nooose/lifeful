package lifeful.member

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원 응답")
data class MemberResponse(
    @Schema(description = "회원 ID", example = "1")
    val id: Long,
    @Schema(description = "이메일")
    val email: Email,
    @Schema(description = "회원 상태")
    val status: MemberStatus,
) {
    companion object {
        fun from(member: Member): MemberResponse {
            return MemberResponse(
                id = member.id,
                email = member.email,
                status = member.status,
            )
        }
    }
}
