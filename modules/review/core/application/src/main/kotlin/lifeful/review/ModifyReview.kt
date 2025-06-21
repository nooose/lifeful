package lifeful.review

import lifeful.review.domain.ReviewRating
import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.shared.ReviewerId

/**
 * 후기 수정 유즈케이스
 * @author hd15807@gmail.com
 */
interface ModifyReview {
    /**
     * 후기 코멘트를 수정한다.
     * @param bookId 책 식별자
     * @param reviewId 후기 식별자
     * @param reviewerId 후기 작성자
     * @param rating 후기 평점
     * @param comment 후기 내용
     */
    fun edit(
        bookId: BookId,
        reviewId: ReviewId,
        reviewerId: ReviewerId,
        rating: ReviewRating,
        comment: String?,
    )
}
