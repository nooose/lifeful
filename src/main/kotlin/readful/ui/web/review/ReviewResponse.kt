package readful.ui.web.review

import readful.core.domain.review.Review
import readful.core.domain.review.ReviewRating
import readful.core.domain.shared.BookId
import readful.core.domain.shared.ReviewId
import readful.core.domain.shared.ReviewerId
import java.time.LocalDateTime

/**
 * 후기 응답 규격
 * @author hd15807@gmail.com
 */
data class ReviewResponse(
    val id: ReviewId,
    val bookId: BookId,
    val rating: ReviewRating,
    val comment: String?,
    val reviewerId: ReviewerId,
    val createdAt: LocalDateTime,
) {

    companion object {
        fun from(domain: Review): ReviewResponse {
            with(domain) {
                return ReviewResponse(
                    id = id,
                    rating = rating,
                    comment = comment,
                    reviewerId = reviewerId,
                    bookId = bookId,
                    createdAt = createdAt,
                )
            }
        }
    }
}
