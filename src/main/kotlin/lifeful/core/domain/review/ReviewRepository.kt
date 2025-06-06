package lifeful.core.domain.review

import lifeful.core.domain.shared.BookId
import lifeful.core.domain.shared.ReviewId
import lifeful.core.domain.shared.ReviewerId

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
