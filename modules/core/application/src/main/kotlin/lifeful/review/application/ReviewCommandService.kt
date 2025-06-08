package lifeful.review.application

import lifefule.review.domain.Review
import lifefule.review.domain.ReviewDuplicateException
import lifefule.review.domain.ReviewNotFoundException
import lifefule.review.domain.ReviewRating
import lifefule.review.domain.ReviewRepository
import lifefule.shared.BookId
import lifefule.shared.ReviewId
import lifefule.shared.ReviewerId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 후기 명령 유즈케이스의 구현체
 * @author hd15807@gmail.com
 */
@Transactional
@Service
class ReviewCommandService(
    private val reviewRepository: ReviewRepository,
) : AddReview, ModifyReview {
    override fun add(review: Review): ReviewId {
        checkDuplicate(review)
        reviewRepository.addReview(review)
        return review.id
    }

    private fun checkDuplicate(review: Review) {
        val existsBook = reviewRepository.findBy(bookId = review.bookId, review.reviewerId)
        if (existsBook != null) {
            throw ReviewDuplicateException("사용자(${review.reviewerId})가 책(${review.bookId})에 이미 후기를 등록했습니다.")
        }
    }

    override fun edit(
        bookId: BookId,
        reviewId: ReviewId,
        reviewerId: ReviewerId,
        rating: ReviewRating,
        comment: String?,
    ) {
        val review = reviewRepository.findBy(bookId, reviewId)
            ?: throw ReviewNotFoundException("후기($reviewId)를 찾을 수 없습니다.")
        review.edit(
            rating = rating,
            comment = comment,
            reviewerId = reviewerId,
        )
    }
}
