package readful.ui.web.review

import readful.core.domain.review.ReviewRating

/**
 * 후기 코멘트 수정 요청 규격
 * @author hd15807@gmail.com
 */
data class ReviewEditRequest(
    val rating: ReviewRating,
    val comment: String?,
)
