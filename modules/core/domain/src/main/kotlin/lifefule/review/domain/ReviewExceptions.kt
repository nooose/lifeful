package lifefule.review.domain

/**
 * 후기 최상위 예외 클래스
 * @author hd15807@gmail.com
 */
open class ReviewException(
    override val message: String,
) : Exception(message)

/**
 * 중복된 후기가 있을 때 발생할 수 있는 예외 클래스
 * @author hd15807@gmail.com
 */
class ReviewDuplicateException(
    override val message: String,
) : ReviewException(message)

/**
 * 후기가 존재하지 않을 때 발생할 수 있는 예외 클래스
 * @author hd15807@gmail.com
 */
class ReviewNotFoundException(
    override val message: String,
) : ReviewException(message)

/**
 * 리뷰의 작성자와 요청자가 다를 때 발생할 수 있는 예외 클래스
 * @author hd15807@gmail.com
 */
class ReviewerMismatchException(
    override val message: String,
) : ReviewException(message)
