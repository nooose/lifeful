package lifeful.review

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.shared.ReviewerId

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
    @field:Schema(description = "후기 작성자 이름")
    val reviewerName: String,
    @field:Schema(description = "후기 생성 시간")
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(review: ReviewQuery): ReviewResponse {
            return ReviewResponse(
                id = review.review.id,
                rating = review.review.rating,
                comment = review.review.comment,
                reviewerId = review.review.reviewerId,
                reviewerName = review.member.name,
                bookId = review.review.bookId,
                createdAt = review.review.createdAt,
            )
        }
    }
}
