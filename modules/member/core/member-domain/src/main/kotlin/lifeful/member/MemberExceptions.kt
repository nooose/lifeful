package lifeful.member

import lifeful.shared.exception.ForbiddenException
import lifeful.shared.exception.ResourceNotFoundException
import lifeful.shared.exception.UnauthorizedException

/**
 * 회원 모듈의 최상위 예외 클래스
 */
open class MemberException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    override fun fillInStackTrace(): Throwable? {
        return this
    }
}

/**
 * 회원을 찾을 수 없는 예외
 */
class MemberNotFoundException(
    message: String = "회원을 찾을 수 없습니다.",
) : ResourceNotFoundException(message)

/**
 * 회원 인증 실패 예외
 */
class MemberAuthenticationFailedException(
    message: String = "회원 인증에 실패했습니다.",
) : UnauthorizedException(message)

/**
 * 회원 권한 부족 예외
 */
class MemberAccessDeniedException(
    message: String = "접근 권한이 없습니다.",
) : ForbiddenException(message)
