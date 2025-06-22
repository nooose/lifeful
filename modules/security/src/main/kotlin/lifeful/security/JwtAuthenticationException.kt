package lifeful.security

import org.springframework.security.core.AuthenticationException

internal class JwtAuthenticationException(
    message: String,
    cause: Throwable? = null,
) : AuthenticationException(message, cause)
