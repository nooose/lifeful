package lifeful.security

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.stereotype.Component

@Component
internal class JwtValidator(
    jwtProperties: JwtProperties,
) {
    private val log = KotlinLogging.logger {}
    private val secretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    /**
     * JWT 토큰을 검증하고 사용자 식별자를 반환한다.
     *
     * @param token JWT 문자열
     * @return 검증된 사용자 식별자
     * @throws JwtAuthenticationException 유효하지 않은 토큰일 때 예외를 던진다.
     * @return 사용자 식별자
     */
    fun validateAndExtractMemberId(token: String): Long {
        val claims = parseToken(token)
        return extractUserId(claims)
    }

    private fun parseToken(token: String): Claims {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: SecurityException) {
            log.warn(e) { "JWT 서명이 잘못되었습니다." }
            throw JwtSignatureException("잘못된 JWT 서명입니다.", e)
        } catch (e: MalformedJwtException) {
            log.warn(e) { "JWT가 손상되었습니다." }
            throw JwtValidationException("손상된 JWT입니다.", e)
        } catch (e: ExpiredJwtException) {
            log.warn(e) { "JWT가 만료되었습니다." }
            throw JwtExpiredException("만료된 JWT 토큰입니다.", e)
        } catch (e: UnsupportedJwtException) {
            log.warn(e) { "지원하지 않는 JWT 토큰입니다." }
            throw JwtValidationException("지원하지 않는 JWT 토큰입니다.", e)
        } catch (e: IllegalArgumentException) {
            log.warn(e) { "JWT가 비어있습니다." }
            throw JwtValidationException("JWT가 비어있습니다.", e)
        }
    }

    private fun extractUserId(claims: Claims): Long {
        return try {
            claims.subject.toLong()
        } catch (e: NumberFormatException) {
            log.warn(e) { "JWT subject가 올바른 식별자 형식이 아닙니다." }
            throw JwtAuthenticationException("잘못된 사용자 정보입니다.", e)
        } catch (e: NullPointerException) {
            log.warn(e) { "JWT subject가 존재하지 않습니다." }
            throw JwtAuthenticationException("사용자 정보가 없습니다.", e)
        }
    }
}
