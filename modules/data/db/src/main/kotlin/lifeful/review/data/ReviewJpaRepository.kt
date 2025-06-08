package lifeful.review.data

import lifefule.review.domain.Review
import lifefule.shared.BookId
import lifefule.shared.ReviewId
import lifefule.shared.ReviewerId
import org.springframework.data.jpa.repository.JpaRepository

internal interface ReviewJpaRepository : JpaRepository<Review, ReviewId> {
    fun findByReviewerIdAndBookId(
        reviewerId: ReviewerId,
        bookId: BookId,
    ): Review?

    fun findAllByBookId(bookId: BookId): List<Review>

    fun findByBookIdAndId(
        bookId: BookId,
        reviewId: ReviewId,
    ): Review?
}
