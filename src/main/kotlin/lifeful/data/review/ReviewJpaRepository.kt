package lifeful.data.review

import lifeful.core.domain.review.Review
import lifeful.core.domain.shared.BookId
import lifeful.core.domain.shared.ReviewId
import lifeful.core.domain.shared.ReviewerId
import org.springframework.data.jpa.repository.JpaRepository

internal interface ReviewJpaRepository : JpaRepository<Review, ReviewId> {

    fun findByReviewerIdAndBookId(reviewerId: ReviewerId, bookId: BookId): Review?
    fun findAllByBookId(bookId: BookId): List<Review>

    fun findByBookIdAndId(bookId: BookId, reviewId: ReviewId): Review?
}
