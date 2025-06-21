package lifeful.review

import lifeful.shared.BookId
import lifeful.shared.ReviewId

/**
 * 후기 검색 유즈케이스
 * @author hd15807@gmail.com
 */
interface FindReview {
    fun byId(
        bookId: BookId,
        reviewId: ReviewId,
    ): ReviewQuery

    fun all(bookId: BookId): List<Review>
}
