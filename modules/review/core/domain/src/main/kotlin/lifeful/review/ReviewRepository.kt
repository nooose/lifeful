package lifeful.review

import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.shared.ReviewerId

/**
 * 후기 저장소
 * @author hd15807@gmail.com
 */
interface ReviewRepository {
    fun save(review: Review): Review

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
