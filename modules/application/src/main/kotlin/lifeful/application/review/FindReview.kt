package lifeful.application.review

import lifefule.domain.review.Review
import lifefule.domain.shared.BookId
import lifefule.domain.shared.ReviewId

/**
 * 후기 검색 유즈케이스
 * @author hd15807@gmail.com
 */
interface FindReview {
    fun byId(
        bookId: BookId,
        reviewId: ReviewId,
    ): Review

    fun all(bookId: BookId): List<Review>
}
