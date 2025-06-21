package lifeful.review

import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.shared.ReviewerId
import org.springframework.data.jpa.repository.JpaRepository

internal interface ReviewJpaRepository : JpaRepository<ReviewEntity, ReviewId> {
    fun findByReviewerIdAndBookId(
        reviewerId: ReviewerId,
        bookId: BookId,
    ): ReviewEntity?

    fun findAllByBookId(bookId: BookId): List<ReviewEntity>

    fun findByBookIdAndId(
        bookId: BookId,
        reviewId: ReviewId,
    ): ReviewEntity?
}
