package lifeful.review

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
class ReviewExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(ReviewException::class)
    fun handleReviewException(
        ex: ReviewException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 리뷰 예외" }

        val status = when (ex) {
            is ReviewNotFoundException -> HttpStatus.NOT_FOUND
            is ReviewAccessDeniedException -> HttpStatus.FORBIDDEN
            is DuplicateReviewException -> HttpStatus.CONFLICT
            is InvalidReviewRatingException -> HttpStatus.BAD_REQUEST
        }

        val errorResponse = ApiErrorResponse(
            status = status.value(),
            error = status.reasonPhrase.uppercase().replace(" ", "_"),
            message = ex.message ?: "후기 처리 중 오류가 발생했습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.status(status).body(errorResponse)
    }
}
