package web.review

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import lifefule.domain.review.Review
import lifefule.domain.review.ReviewRating
import lifefule.domain.shared.BookId
import lifefule.domain.shared.ReviewId
import lifefule.domain.shared.ReviewerId

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
