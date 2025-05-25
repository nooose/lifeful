package readful.ui.web.review

import readful.core.domain.review.Review
import readful.core.domain.review.ReviewRating
import readful.core.domain.shared.BookId
import readful.core.domain.shared.ReviewerId

/**
 * 후기 요청 규격
 * @author hd15807@gmail.com
 */
data class ReviewAddRequest(
    val content: String?,
    val rating: ReviewRating,
) {

    fun toDomain(
        bookId: BookId,
        reviewerId: ReviewerId,
    ): Review {
        return Review(
            rating = rating,
            comment = content,
            reviewerId = reviewerId,
            bookId = bookId,
        )
    }
}
