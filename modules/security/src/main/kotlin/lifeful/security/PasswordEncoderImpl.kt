package lifeful.security

import java.security.MessageDigest
import lifeful.member.PasswordEncoder
import org.springframework.stereotype.Component

@Component
internal class PasswordEncoderImpl : PasswordEncoder {
    override fun encode(rawPassword: String): String {
        return hash(rawPassword)
    }

    override fun matches(
        rawPassword: String,
        encodedPassword: String,
    ): Boolean {
        return hash(rawPassword) == encodedPassword
    }

    private fun hash(rawPassword: String): String {
        val digest = SHA_512.digest(rawPassword.toByteArray(Charsets.UTF_8))
        return digest.toHex()
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

    companion object {
        private val SHA_512 = MessageDigest.getInstance("SHA-512")
    }
}
