package lifeful.api.review

import io.swagger.v3.oas.annotations.media.Schema
import lifefule.review.domain.Review
import lifefule.review.domain.ReviewRating
import lifefule.shared.ReviewId
import lifefule.shared.ReviewerId

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
