package lifeful.security

import java.time.Duration
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("lifeful.security.jwt")
internal data class JwtProperties(
    val secretKey: String,
    val expirationTime: Duration,
) {
    init {
        require(secretKey.length >= MIN_KEY_LEN) {
            "JWT 비밀키는 최소 ${MIN_KEY_LEN}자 이상이어야 합니다."
        }
    }

    companion object {
        private const val MIN_KEY_LEN = 32
    }
}
