package lifeful.review.domain

import java.time.LocalDateTime
import lifeful.shared.BaseModel
import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.shared.ReviewerId

/**
 * 후기 도메인 모델
 * @author hd15807@gmail.com
 */
data class Review(
    var rating: ReviewRating,
    var comment: String?,
    val reviewerId: ReviewerId,
    val bookId: BookId,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val modifiedAt: LocalDateTime = LocalDateTime.now(),
    val id: ReviewId = ReviewId(),
) : BaseModel {
    fun edit(
        rating: ReviewRating,
        comment: String?,
        reviewerId: ReviewerId,
    ): Review {
        checkOwner(reviewerId)
        return Review(
            rating = rating,
            comment = comment,
            reviewerId = reviewerId,
            bookId = bookId,
            id = id,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
    }

    private fun checkOwner(reviewerId: ReviewerId) {
        if (reviewerId != this.reviewerId) {
            throw ReviewerMismatchException("후기(${this.id})는 작성자만 수정할 수 있습니다.")
        }
    }
}
