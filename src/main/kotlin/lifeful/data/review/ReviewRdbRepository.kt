package lifeful.data.review

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import lifeful.core.domain.review.Review
import lifeful.core.domain.review.ReviewRepository
import lifeful.core.domain.shared.BookId
import lifeful.core.domain.shared.ReviewId
import lifeful.core.domain.shared.ReviewerId

@Repository
internal class ReviewRdbRepository(
    private val jpaRepository: ReviewJpaRepository
) : ReviewRepository {

    @Transactional
    override fun addReview(review: Review) {
        jpaRepository.save(review)
    }

    override fun findBy(reviewerId: ReviewerId, bookId: BookId): Review? {
        return jpaRepository.findByReviewerIdAndBookId(reviewerId, bookId)
    }

    override fun findBy(reviewId: ReviewId): Review? {
        return jpaRepository.findByIdOrNull(reviewId)
    }

    override fun findAll(bookId: BookId): List<Review> {
        return jpaRepository.findAllByBookId(bookId)
    }
}
