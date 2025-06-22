package lifeful.book

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import lifeful.support.ApiErrorResponse
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Order(1)
@RestControllerAdvice
class BookExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(BookException::class)
    fun handleBookException(
        ex: BookException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 책 예외: ${ex.message}" }

        val status = when (ex) {
            is BookNotFoundException -> HttpStatus.NOT_FOUND
            is BookExternalApiException -> HttpStatus.BAD_GATEWAY
            is InvalidBookDataException -> HttpStatus.BAD_REQUEST
            else -> HttpStatus.BAD_REQUEST
        }

        val errorResponse = ApiErrorResponse(
            status = status.value(),
            error = status.reasonPhrase.uppercase().replace(" ", "_"),
            message = ex.message ?: "책 처리 중 오류가 발생했습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.status(status).body(errorResponse)
    }
}
