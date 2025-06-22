package lifeful.review

/**
 * 리뷰 모듈의 최상위 예외 클래스
 */
sealed class ReviewException(
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    override fun fillInStackTrace(): Throwable? {
        return this
    }
}

/**
 * 리뷰를 찾을 수 없는 예외
 */
class ReviewNotFoundException(
    message: String = "리뷰를 찾을 수 없습니다.",
    cause: Throwable? = null,
) : ReviewException(message, cause)

/**
 * 리뷰 권한 부족 예외 (다른 사용자의 리뷰 수정/삭제 시도)
 */
class ReviewAccessDeniedException(
    message: String = "리뷰에 대한 권한이 없습니다.",
    cause: Throwable? = null,
) : ReviewException(message, cause)

/**
 * 중복 리뷰 생성 예외
 */
class DuplicateReviewException(
    message: String = "이미 해당 책에 대한 리뷰가 존재합니다.",
    cause: Throwable? = null,
) : ReviewException(message, cause)

/**
 * 잘못된 리뷰 평점 예외
 */
class InvalidReviewRatingException(
    message: String = "유효하지 않은 평점입니다.",
    cause: Throwable? = null,
) : ReviewException(message, cause)
