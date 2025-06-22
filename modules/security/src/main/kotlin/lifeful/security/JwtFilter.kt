package lifeful.security

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.web.filter.OncePerRequestFilter

internal class JwtFilter(
    private val permitRequest: RequestMatcher,
    private val jwtValidator: JwtValidator,
    private val authenticationEntryPoint: AuthenticationEntryPoint,
) : OncePerRequestFilter() {
    private val log = KotlinLogging.logger {}

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return permitRequest.matches(request)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val authentication = authenticateRequest(request)
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } catch (e: AuthenticationException) {
            handleAuthenticationException(request, response, e)
        } catch (e: Exception) {
            log.error(e) { "[${request.requestURI}] JWT 토큰 검증 중 예상치 못한 오류가 발생했습니다." }
            val authException = JwtAuthenticationException("토큰 검증 중 오류가 발생했습니다.", e)
            handleAuthenticationException(request, response, authException)
        }
    }

    private fun authenticateRequest(request: HttpServletRequest): Authentication {
        val token = extractToken(request)
        val userId = jwtValidator.validateAndExtractMemberId(token)

        log.debug { "[${request.requestURI}] JWT 토큰 검증 성공: userId=$userId" }

        // TODO: 커스텀 Authentication 토큰 객체 만들기
        return UsernamePasswordAuthenticationToken.authenticated(
            userId,
            null,
            emptyList(),
        )
    }

    private fun extractToken(request: HttpServletRequest): String {
        val token = request.bearerToken()
        if (token == null) {
            log.debug { "[${request.requestURI}] Bearer token이 없습니다." }
            throw JwtAuthenticationException("Bearer token이 필요합니다.")
        }
        return token
    }

    private fun HttpServletRequest.bearerToken(): String? {
        val header = this.getHeader(HttpHeaders.AUTHORIZATION)
        if (header.isNullOrBlank() || !header.startsWith(BEARER_TOKEN_PREFIX)) {
            return null
        }
        return header.split(" ")[1]
    }

    private fun handleAuthenticationException(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        SecurityContextHolder.clearContext()
        authenticationEntryPoint.commence(request, response, authException)
    }

    companion object {
        const val BEARER_TOKEN_PREFIX = "Bearer "
    }
}
