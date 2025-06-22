package lifeful.support

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 유효성 검증 실패: ${ex.message}" }

        val fieldErrors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "유효하지 않은 값입니다.")
        }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BAD_REQUEST",
            message = "입력값 검증에 실패했습니다.",
            path = request.requestURI,
            fields = fieldErrors,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(
        ex: BindException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 바인딩 오류: ${ex.message}" }

        val fieldErrors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "유효하지 않은 값입니다.")
        }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BAD_REQUEST",
            message = "요청 파라미터 바인딩에 실패했습니다.",
            path = request.requestURI,
            fields = fieldErrors,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 제약 조건 위반: ${ex.message}" }

        val violations = ex.constraintViolations.associate {
            it.propertyPath.toString() to it.message
        }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BAD_REQUEST",
            message = "제약 조건을 위반했습니다.",
            path = request.requestURI,
            fields = violations,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        ex: HttpRequestMethodNotSupportedException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 지원하지 않는 HTTP 메서드: ${ex.method}" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.METHOD_NOT_ALLOWED.value(),
            error = "METHOD_NOT_ALLOWED",
            message = "지원하지 않는 HTTP 메서드입니다: ${ex.method}",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 핸들러를 찾을 수 없음" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = "NOT_FOUND",
            message = "요청한 리소스를 찾을 수 없습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(
        ex: MissingServletRequestParameterException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 필수 파라미터 누락: ${ex.parameterName}" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BAD_REQUEST",
            message = "필수 요청 파라미터가 누락되었습니다: ${ex.parameterName}",
            path = request.requestURI,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        ex: MethodArgumentTypeMismatchException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 파라미터 타입 불일치: ${ex.name}" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BAD_REQUEST",
            message = "파라미터 타입이 올바르지 않습니다: ${ex.name}",
            path = request.requestURI,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] HTTP 메시지를 읽을 수 없음" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BAD_REQUEST",
            message = "요청 본문을 읽을 수 없습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception,
        request: HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.error(ex) { "[${request.requestURI}] 예상치 못한 오류 발생" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "INTERNAL_SERVER_ERROR",
            message = "서버 내부 오류가 발생했습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
