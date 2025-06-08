package lifeful.review.application

import lifefule.review.domain.Review
import lifefule.review.domain.ReviewNotFoundException
import lifefule.review.domain.ReviewRepository
import lifefule.shared.BookId
import lifefule.shared.ReviewId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 후기 조회 유즈케이스의 구현체
 * @author hd15807@gmail.com
 */
@Transactional(readOnly = false)
@Service
class ReviewQueryService(
    private val reviewRepository: ReviewRepository,
) : FindReview {
    override fun byId(
        bookId: BookId,
        reviewId: ReviewId,
    ): Review {
        return reviewRepository.findBy(bookId = bookId, reviewId = reviewId)
            ?: throw ReviewNotFoundException("후기($reviewId)를 찾을 수 없습니다.")
    }

    override fun all(bookId: BookId): List<Review> {
        return reviewRepository.findAll(bookId)
    }
}
