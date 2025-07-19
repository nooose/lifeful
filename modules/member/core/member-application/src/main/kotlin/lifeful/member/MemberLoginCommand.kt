package lifeful.member

data class MemberLoginCommand(
    val email: String,
    val password: String,
)
