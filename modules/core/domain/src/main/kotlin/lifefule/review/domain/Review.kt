package lifefule.review.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lifefule.shared.BaseEntity
import lifefule.shared.BookId
import lifefule.shared.ReviewId
import lifefule.shared.ReviewerId

/**
 * 후기 도메인 모델
 * @author hd15807@gmail.com
 */
@Entity
class Review(
    var rating: ReviewRating,
    var comment: String?,
    val reviewerId: ReviewerId,
    val bookId: BookId,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: ReviewId = ReviewId(),
) : BaseEntity() {
    fun edit(
        rating: ReviewRating,
        comment: String?,
        reviewerId: ReviewerId,
    ) {
        checkOwner(reviewerId)
        this.rating = rating
        this.comment = comment
    }

    private fun checkOwner(reviewerId: ReviewerId) {
        if (reviewerId != this.reviewerId) {
            throw ReviewerMismatchException("후기(${this.id})는 작성자만 수정할 수 있습니다.")
        }
    }
}
