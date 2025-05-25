package readful.core.domain.review

import readful.core.domain.shared.BookId
import readful.core.domain.shared.ReviewId
import readful.core.domain.shared.ReviewerId

/**
 * 후기 저장소
 * @author hd15807@gmail.com
 */
interface ReviewRepository {

    fun addReview(review: Review)

    fun findBy(reviewerId: ReviewerId, bookId: BookId): Review?
    fun findBy(reviewId: ReviewId): Review?
    fun findAll(bookId: BookId): List<Review>
}
