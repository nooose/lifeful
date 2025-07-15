package lifeful.member

interface PasswordEncoder {
    fun encode(rawPassword: String): String

    fun matches(
        rawPassword: String,
        encodedPassword: String,
    ): Boolean
}
