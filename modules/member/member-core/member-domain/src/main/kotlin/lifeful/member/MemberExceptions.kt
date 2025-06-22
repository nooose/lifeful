package lifeful.member

/**
 * 멤버 모듈의 최상위 예외 클래스
 */
sealed class MemberException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    override fun fillInStackTrace(): Throwable? {
        return this
    }
}

/**
 * 멤버를 찾을 수 없는 예외
 */
class MemberNotFoundException(
    message: String = "멤버를 찾을 수 없습니다.",
    cause: Throwable? = null,
) : MemberException(message, cause)

/**
 * 멤버 인증 실패 예외
 */
class MemberAuthenticationFailedException(
    message: String = "멤버 인증에 실패했습니다.",
    cause: Throwable? = null,
) : MemberException(message, cause)

/**
 * 멤버 권한 부족 예외
 */
class MemberAccessDeniedException(
    message: String = "접근 권한이 없습니다.",
    cause: Throwable? = null,
) : MemberException(message, cause)
