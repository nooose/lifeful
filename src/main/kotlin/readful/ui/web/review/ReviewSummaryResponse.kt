package readful.ui.web.review

import readful.core.domain.review.Review
import readful.core.domain.review.ReviewRating
import readful.core.domain.shared.ReviewId
import readful.core.domain.shared.ReviewerId

/**
 * 후기 목록 응답 규격
 * @author hd15807@gmail.com
 */
data class ReviewSummaryResponse(
    val id: ReviewId,
    val rating: ReviewRating,
    val comment: String?,
    val reviewerId: ReviewerId,
) {

    companion object {
        fun from(domain: Review): ReviewSummaryResponse {
            return ReviewSummaryResponse(
                rating = domain.rating,
                comment = domain.comment,
                reviewerId = domain.reviewerId,
                id = domain.id,
            )
        }
    }
}
