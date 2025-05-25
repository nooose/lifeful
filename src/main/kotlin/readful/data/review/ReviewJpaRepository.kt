package readful.data.review

import org.springframework.data.jpa.repository.JpaRepository
import readful.core.domain.review.Review
import readful.core.domain.shared.BookId
import readful.core.domain.shared.ReviewId
import readful.core.domain.shared.ReviewerId

internal interface ReviewJpaRepository : JpaRepository<Review, ReviewId> {

    fun findByReviewerIdAndBookId(reviewerId: ReviewerId, bookId: BookId): Review?
    fun findAllByBookId(bookId: BookId): List<Review>
}
