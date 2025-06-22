package lifeful.book

/**
 * 책 최상위 예외 클래스
 */
sealed class BookException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    override fun fillInStackTrace(): Throwable? {
        return this
    }
}

/**
 * 책을 찾을 수 없는 예외
 */
class BookNotFoundException(
    message: String = "책을 찾을 수 없습니다.",
    cause: Throwable? = null,
) : BookException(message, cause)

/**
 * 외부 API 연동 실패 예외
 */
class BookExternalApiException(
    message: String = "외부 API 연동에 실패했습니다.",
    cause: Throwable? = null,
) : BookException(message, cause)

/**
 * 잘못된 책 정보 예외
 */
class InvalidBookDataException(
    message: String = "유효하지 않은 책 정보입니다.",
    cause: Throwable? = null,
) : BookException(message, cause)
