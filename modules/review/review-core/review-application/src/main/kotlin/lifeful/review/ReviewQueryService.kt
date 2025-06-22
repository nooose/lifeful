package lifeful.review

import lifeful.member.MemberFinder
import lifeful.shared.BookId
import lifeful.shared.MemberId
import lifeful.shared.ReviewId
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
    private val memberFinder: MemberFinder,
) : FindReview {
    override fun byId(
        bookId: BookId,
        reviewId: ReviewId,
    ): ReviewQuery {
        val review = (
            reviewRepository.findBy(bookId = bookId, reviewId = reviewId)
                ?: throw ReviewNotFoundException("후기($reviewId)를 찾을 수 없습니다.")
        )
        val member = memberFinder.byId(MemberId(review.reviewerId.value))
            ?: throw IllegalStateException("사용자($reviewId)를 찾을 수 없습니다.")
        return ReviewQuery(
            review = review,
            member = member,
        )
    }

    override fun all(bookId: BookId): List<Review> {
        return reviewRepository.findAll(bookId)
    }
}
