package lifeful.member.command

data class MemberRegisterCommand(
    val nickname: String,
    val email: String,
    val password: String,
)
