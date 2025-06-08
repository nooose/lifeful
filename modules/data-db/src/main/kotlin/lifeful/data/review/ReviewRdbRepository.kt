package lifeful.data.review

import lifefule.domain.review.Review
import lifefule.domain.review.ReviewRepository
import lifefule.domain.shared.BookId
import lifefule.domain.shared.ReviewId
import lifefule.domain.shared.ReviewerId
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class ReviewRdbRepository(
    private val jpaRepository: ReviewJpaRepository,
) : ReviewRepository {
    @Transactional
    override fun addReview(review: Review) {
        jpaRepository.save(review)
    }

    override fun findBy(
        bookId: BookId,
        reviewerId: ReviewerId,
    ): Review? {
        return jpaRepository.findByReviewerIdAndBookId(reviewerId, bookId)
    }

    override fun findBy(
        bookId: BookId,
        reviewId: ReviewId,
    ): Review? {
        return jpaRepository.findByBookIdAndId(bookId, reviewId)
    }

    override fun findAll(bookId: BookId): List<Review> {
        return jpaRepository.findAllByBookId(bookId)
    }
}
