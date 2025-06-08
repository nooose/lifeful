package lifeful.api.review

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import lifefule.review.domain.Review
import lifefule.review.domain.ReviewRating
import lifefule.shared.BookId
import lifefule.shared.ReviewId
import lifefule.shared.ReviewerId

@Schema(description = "후기 응답")
data class ReviewResponse(
    @field:Schema(description = "후기 식별자")
    val id: ReviewId,
    @field:Schema(description = "책 식별자")
    val bookId: BookId,
    @field:Schema(description = "평점")
    val rating: ReviewRating,
    @field:Schema(description = "후기 내용")
    val comment: String?,
    @field:Schema(description = "후기 작성자 식별자")
    val reviewerId: ReviewerId,
    @field:Schema(description = "후기 생성 시간")
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
