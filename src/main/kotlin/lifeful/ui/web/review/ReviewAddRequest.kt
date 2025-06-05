package lifeful.ui.web.review

import lifeful.core.domain.review.Review
import lifeful.core.domain.review.ReviewRating
import lifeful.core.domain.shared.BookId
import lifeful.core.domain.shared.ReviewerId

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
