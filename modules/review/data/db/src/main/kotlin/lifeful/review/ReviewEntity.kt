package lifeful.review

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import lifeful.review.domain.Review
import lifeful.review.domain.ReviewRating
import lifeful.shared.BaseModel
import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.shared.ReviewerId

/**
 * 리뷰 엔티티
 * @author hd15807@gmail.com
 */
@Entity
internal class ReviewEntity(
    var rating: ReviewRating,
    var comment: String?,
    val reviewerId: ReviewerId,
    val bookId: BookId,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: ReviewId = ReviewId(),
    override var createdAt: LocalDateTime = LocalDateTime.now(),
    override var modifiedAt: LocalDateTime = LocalDateTime.now(),
) : BaseModel {
    fun toDomain(): Review {
        return Review(
            id = id,
            rating = rating,
            comment = comment,
            reviewerId = reviewerId,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
            bookId = bookId,
        )
    }

    companion object {
        fun from(review: Review): ReviewEntity {
            return ReviewEntity(
                rating = review.rating,
                comment = review.comment,
                reviewerId = review.reviewerId,
                bookId = review.bookId,
                id = review.id,
                createdAt = review.createdAt,
                modifiedAt = review.modifiedAt,
            )
        }
    }
}
