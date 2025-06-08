package lifefule.domain.review

import lifefule.domain.shared.BookId
import lifefule.domain.shared.ReviewId
import lifefule.domain.shared.ReviewerId

/**
 * 후기 저장소
 * @author hd15807@gmail.com
 */
interface ReviewRepository {
    fun addReview(review: Review)

    fun findBy(
        bookId: BookId,
        reviewerId: ReviewerId,
    ): Review?

    fun findBy(
        bookId: BookId,
        reviewId: ReviewId,
    ): Review?

    fun findAll(bookId: BookId): List<Review>
}
