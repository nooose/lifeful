package lifeful.shared

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.ConstraintViolationException
import java.time.Instant
import lifeful.shared.exception.DomainIllegalStateException
import lifeful.shared.exception.DuplicateException
import lifeful.shared.exception.ForbiddenException
import lifeful.shared.exception.InvalidUserInputException
import lifeful.shared.exception.ResourceNotFoundException
import lifeful.shared.exception.UnauthorizedException
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(
        value = [
            BindException::class,
            ConstraintViolationException::class,
            MethodArgumentTypeMismatchException::class,
            InvalidUserInputException::class,
            IllegalArgumentException::class,
        ],
    )
    fun handleBadRequest(ex: Exception): ProblemDetail {
        return getProblemDetail(HttpStatus.BAD_REQUEST, ex)
    }

    @ExceptionHandler(
        value = [
            ResourceNotFoundException::class,
        ],
    )
    fun handleNotFound(ex: Exception): ProblemDetail {
        return getProblemDetail(HttpStatus.NOT_FOUND, ex)
    }

    @ExceptionHandler(DomainIllegalStateException::class)
    fun handle(ex: DomainIllegalStateException): ProblemDetail {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    @ExceptionHandler(DuplicateException::class)
    fun handle(ex: DuplicateException): ProblemDetail {
        return getProblemDetail(HttpStatus.CONFLICT, ex)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handle(ex: UnauthorizedException): ProblemDetail {
        return getProblemDetail(HttpStatus.UNAUTHORIZED, ex)
    }

    @ExceptionHandler(ForbiddenException::class)
    fun handle(ex: ForbiddenException): ProblemDetail {
        return getProblemDetail(HttpStatus.FORBIDDEN, ex)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handle(ex: AccessDeniedException): ProblemDetail {
        return getProblemDetail(HttpStatus.FORBIDDEN, ex)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handle(ex: AuthenticationException): ProblemDetail {
        return getProblemDetail(HttpStatus.UNAUTHORIZED, ex)
    }

    @ExceptionHandler(Exception::class)
    fun handle(ex: Exception): ProblemDetail {
        return getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    private fun getProblemDetail(
        status: HttpStatus,
        ex: Exception,
    ): ProblemDetail {
        log.error(ex) { ex.message }
        val problemDetail = ProblemDetail.forStatusAndDetail(status, ex.message ?: "알 수 없는 오류가 발생했습니다.")
        problemDetail.setProperty("timestamp", Instant.now().toString())
        problemDetail.setProperty("exception", ex.javaClass.simpleName)
        return problemDetail
    }
}
