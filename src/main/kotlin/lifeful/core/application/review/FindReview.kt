package lifeful.core.application.review

import lifeful.core.domain.review.Review
import lifeful.core.domain.shared.BookId
import lifeful.core.domain.shared.ReviewId

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
