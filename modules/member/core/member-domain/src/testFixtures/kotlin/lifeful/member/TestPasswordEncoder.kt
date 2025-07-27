package lifeful.member

class TestPasswordEncoder : PasswordEncoder {
    override fun encode(rawPassword: String): String {
        return "${rawPassword}Hash"
    }

    override fun matches(
        rawPassword: String,
        encodedPassword: String,
    ): Boolean {
        return encode(rawPassword) == encodedPassword
    }
}
