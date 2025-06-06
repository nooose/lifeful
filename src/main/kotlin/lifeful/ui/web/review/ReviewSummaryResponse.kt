package lifeful.ui.web.review

import io.swagger.v3.oas.annotations.media.Schema
import lifeful.core.domain.review.Review
import lifeful.core.domain.review.ReviewRating
import lifeful.core.domain.shared.ReviewId
import lifeful.core.domain.shared.ReviewerId

@Schema(description = "후기 목록 응답")
data class ReviewSummaryResponse(
    @field:Schema(description = "후기 식별자")
    val id: ReviewId,
    @field:Schema(description = "평점")
    val rating: ReviewRating,
    @field:Schema(description = "후기 내용")
    val comment: String?,
    @field:Schema(description = "후기 작성자 식별자")
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
