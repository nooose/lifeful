package lifeful.review

import io.swagger.v3.oas.annotations.media.Schema
import lifeful.shared.BookId
import lifeful.shared.ReviewerId

@Schema(description = "후기 등록 요청")
data class ReviewAddRequest(
    @field:Schema(description = "후기 내용")
    val comment: String?,
    @field:Schema(
        description = "평점(0.5 단위)",
        example = "0.5",
        minimum = "0.0",
        maximum = "5.0",
    )
    val rating: ReviewRating,
) {
    fun toDomain(
        bookId: BookId,
        reviewerId: ReviewerId,
    ): Review {
        return Review(
            rating = rating,
            comment = comment,
            reviewerId = reviewerId,
            bookId = bookId,
        )
    }
}
