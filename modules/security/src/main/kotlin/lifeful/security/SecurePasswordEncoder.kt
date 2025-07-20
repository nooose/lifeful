package lifeful.security

import lifeful.member.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
internal class SecurePasswordEncoder(
    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder(),
) : PasswordEncoder {
    override fun encode(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    override fun matches(
        rawPassword: String,
        encodedPassword: String,
    ): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}
