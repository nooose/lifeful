package lifeful.shared

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.ConstraintViolationException
import lifeful.shared.exception.DomainIllegalStateException
import lifeful.shared.exception.DuplicateException
import lifeful.shared.exception.InvalidUserInputException
import lifeful.shared.exception.ResourceNotFoundException
import lifeful.shared.exception.UnauthorizedException
import org.springframework.core.Ordered
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException

@org.springframework.core.annotation.Order(org.springframework.core.Ordered.LOWEST_PRECEDENCE)
@org.springframework.web.bind.annotation.RestControllerAdvice
internal class GlobalExceptionHandler {
    private val log = KotlinLogging.logger {}

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 유효성 검증 실패: ${ex.message}" }

        val fieldErrors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "유효하지 않은 값입니다.")
        }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "입력값 검증에 실패했습니다.",
            path = request.requestURI,
            fields = fieldErrors,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BindException::class)
    fun handleBindException(
        ex: BindException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 바인딩 오류: ${ex.message}" }

        val fieldErrors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "유효하지 않은 값입니다.")
        }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "요청 파라미터 바인딩에 실패했습니다.",
            path = request.requestURI,
            fields = fieldErrors,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 제약 조건 위반: ${ex.message}" }

        val violations = ex.constraintViolations.associate {
            it.propertyPath.toString() to it.message
        }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "제약 조건을 위반했습니다.",
            path = request.requestURI,
            fields = violations,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(
        ex: HttpRequestMethodNotSupportedException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 지원하지 않는 HTTP 메서드: ${ex.method}" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.METHOD_NOT_ALLOWED.value(),
            error = HttpStatus.METHOD_NOT_ALLOWED.reasonPhrase,
            message = "지원하지 않는 HTTP 메서드입니다: ${ex.method}",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 핸들러를 찾을 수 없음" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = "요청한 리소스를 찾을 수 없습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(
        ex: MissingServletRequestParameterException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 필수 파라미터 누락: ${ex.parameterName}" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "필수 요청 ${ex.parameterName} 파라미터가 누락되었습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        ex: MethodArgumentTypeMismatchException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] 파라미터 타입 불일치: ${ex.name}" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "${ex.name} 파라미터 타입이 올바르지 않습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(
        ex: HttpMessageNotReadableException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.warn(ex) { "[${request.requestURI}] HTTP 메시지를 읽을 수 없음" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = "요청 본문을 읽을 수 없습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidUserInputException::class)
    fun handleException(
        ex: InvalidUserInputException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.error(ex) { "예외 발생" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = ex.message ?: "",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DomainIllegalStateException::class)
    fun handleException(
        ex: DomainIllegalStateException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.error(ex) { "예외 발생" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = ex.message ?: "",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DuplicateException::class)
    fun handleException(
        ex: DuplicateException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.error(ex) { "리소스 중복 예외 발생" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.CONFLICT.value(),
            error = HttpStatus.CONFLICT.reasonPhrase,
            message = ex.message ?: "",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException::class)
    fun handleException(
        ex: ResourceNotFoundException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.error(ex) { "리소스를 찾을 수 없습니다." }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.reasonPhrase,
            message = ex.message ?: "",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException::class)
    fun handleException(
        ex: UnauthorizedException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        val errorResponse = ApiErrorResponse(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = HttpStatus.UNAUTHORIZED.reasonPhrase,
            message = ex.message ?: "",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException::class)
    fun handleException(
        ex: IllegalArgumentException,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.error(ex) { ex.message }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = ex.message ?: "",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception::class)
    fun handleException(
        ex: Exception,
        request: jakarta.servlet.http.HttpServletRequest,
    ): ResponseEntity<ApiErrorResponse> {
        log.error(ex) { "[${request.requestURI}] 예상치 못한 오류 발생" }

        val errorResponse = ApiErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase,
            message = "서버 내부 오류가 발생했습니다.",
            path = request.requestURI,
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
