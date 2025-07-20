package lifeful.member.command

data class MemberLoginCommand(
    val email: String,
    val password: String,
)
