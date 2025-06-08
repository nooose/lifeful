package lifeful.review.application

import lifefule.review.domain.Review
import lifefule.shared.ReviewId

/**
 * 후기 등록 유즈케이스
 * @author hd15807@gmail.com
 */
interface AddReview {
    /**
     * 후기를 등록한다.
     * @param review 등록할 후기 도메인 모델
     * @return 등록된 후기 식별자
     */
    fun add(review: Review): ReviewId
}
