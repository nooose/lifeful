package lifeful.review

import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.shared.ReviewerId
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class ReviewRepositoryImpl(
    private val jpaRepository: ReviewJpaRepository,
) : ReviewRepository {
    @Transactional
    override fun save(review: Review): Review {
        val entity = ReviewEntity.from(review)
        jpaRepository.save(entity)
        return entity.toDomain()
    }

    override fun findBy(
        bookId: BookId,
        reviewerId: ReviewerId,
    ): Review? {
        return jpaRepository.findByReviewerIdAndBookId(reviewerId, bookId)
            ?.toDomain()
    }

    override fun findBy(
        bookId: BookId,
        reviewId: ReviewId,
    ): Review? {
        return jpaRepository.findByBookIdAndId(bookId, reviewId)
            ?.toDomain()
    }

    override fun findAll(bookId: BookId): List<Review> {
        return jpaRepository.findAllByBookId(bookId)
            .map { it.toDomain() }
    }
}
