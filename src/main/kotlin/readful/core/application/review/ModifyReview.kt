package readful.core.application.review

import readful.core.domain.shared.ReviewId
import readful.core.domain.shared.ReviewerId

/**
 * 후기 수정 유즈케이스
 * @author hd15807@gmail.com
 */
interface ModifyReview {

    /**
     * 후기 코멘트를 수정한다.
     * @param reviewId 후기 식별자
     * @param reviewerId 후기 작성자
     * @param comment 후기 내용
     */
    fun edit(reviewId: ReviewId, reviewerId: ReviewerId, comment: String?)
}
