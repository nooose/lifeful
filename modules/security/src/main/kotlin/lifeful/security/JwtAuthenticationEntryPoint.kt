package lifeful.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
internal class JwtAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    private val log = KotlinLogging.logger {}

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        log.warn(authException) { "[${request.requestURI}] 인증 실패" }
        
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"

        // TODO: 표준 에러 규격 객체 만들기
        val errorResponse = mapOf(
            "error" to HttpStatus.UNAUTHORIZED.name,
            "message" to (authException.message ?: "인증이 필요합니다."),
            "status" to HttpStatus.UNAUTHORIZED.value(),
            "path" to request.requestURI
        )
        
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
} 
