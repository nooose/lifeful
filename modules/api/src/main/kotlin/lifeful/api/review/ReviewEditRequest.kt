package lifeful.api.review

import io.swagger.v3.oas.annotations.media.Schema
import lifefule.review.domain.ReviewRating

@Schema(description = "후기 수정 요청")
data class ReviewEditRequest(
    @field:Schema(
        description = "평점(0.5 단위)",
        example = "0.5",
        minimum = "0.0",
        maximum = "5.0",
    )
    val rating: ReviewRating,
    @field:Schema(description = "후기 내용")
    val comment: String?,
)
