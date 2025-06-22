package lifeful.security

import java.time.Duration
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("lifeful.security.jwt")
internal data class JwtProperties(
    val secretKey: String,
    val expirationTime: Duration,
)
