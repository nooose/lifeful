package lifeful.data.review

import lifefule.domain.review.Review
import lifefule.domain.shared.BookId
import lifefule.domain.shared.ReviewId
import lifefule.domain.shared.ReviewerId
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
