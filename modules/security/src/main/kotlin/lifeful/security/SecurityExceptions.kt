package lifeful.security

import org.springframework.security.core.AuthenticationException

/**
 * JWT 인증 관련 예외
 */
class JwtAuthenticationException(
    message: String,
    cause: Throwable? = null,
) : AuthenticationException(message, cause) {
    override fun fillInStackTrace(): Throwable? {
        return this
    }
}

/**
 * JWT 토큰 검증 실패 예외
 */
class JwtValidationException(
    message: String,
    cause: Throwable? = null,
) : AuthenticationException(message, cause)

/**
 * JWT 토큰 만료 예외
 */
class JwtExpiredException(
    message: String = "JWT 토큰이 만료되었습니다.",
    cause: Throwable? = null,
) : AuthenticationException(message, cause)

/**
 * JWT 서명 오류 예외
 */
class JwtSignatureException(
    message: String = "JWT 서명이 잘못되었습니다.",
    cause: Throwable? = null,
) : AuthenticationException(message, cause)
