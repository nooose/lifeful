package readful.core.application.review

import readful.core.domain.review.Review
import readful.core.domain.shared.BookId
import readful.core.domain.shared.ReviewId

/**
 * 후기 검색 유즈케이스
 * @author hd15807@gmail.com
 */
interface FindReview {

    fun byId(reviewId: ReviewId): Review
    fun all(bookId: BookId): List<Review>
}
