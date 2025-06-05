package lifeful.core.application.review

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import lifeful.core.domain.review.*
import lifeful.core.domain.shared.ReviewId
import lifeful.core.domain.shared.ReviewerId

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
        val existsBook = reviewRepository.findBy(review.reviewerId, review.bookId)
        if (existsBook != null) {
            throw ReviewDuplicateException("사용자(${review.reviewerId})가 책(${review.bookId})에 이미 후기를 등록했습니다.")
        }
    }

    override fun edit(
        reviewId: ReviewId,
        reviewerId: ReviewerId,
        rating: ReviewRating,
        comment: String?
    ) {
        val review = reviewRepository.findBy(reviewId)
            ?: throw ReviewNotFoundException("후기($reviewId)를 찾을 수 없습니다.")
        review.edit(
            rating = rating,
            comment = comment,
            reviewerId = reviewerId,
        )
    }
}
