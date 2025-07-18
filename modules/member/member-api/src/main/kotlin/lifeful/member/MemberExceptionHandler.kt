package lifeful.member

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import lifeful.shared.ApiErrorResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(1)
@RestControllerAdvice
class MemberExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(MemberException::class)
    fun handleMemberException(
        ex: MemberException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 사용자 예외: ${ex.message}" }

        val status = when (ex) {
            is MemberNotFoundException -> HttpStatus.NOT_FOUND
            is MemberAccessDeniedException -> HttpStatus.FORBIDDEN
            is MemberAuthenticationFailedException -> HttpStatus.UNAUTHORIZED
            is MemberPasswordMisMatchException -> HttpStatus.FORBIDDEN
            else -> HttpStatus.INTERNAL_SERVER_ERROR
        }

        val errorResponse = ApiErrorResponse(
            status = status.value(),
            error = status.reasonPhrase,
            message = ex.message ?: "사용자 처리 중 오류가 발생했습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.status(status).body(errorResponse)
    }
}
