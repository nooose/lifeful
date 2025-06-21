package lifeful.review

import lifeful.review.domain.Review
import lifeful.shared.BookId
import lifeful.shared.ReviewId
import org.springframework.modulith.NamedInterface

/**
 * 후기 검색 유즈케이스
 * @author hd15807@gmail.com
 */
@NamedInterface("find-review")
interface FindReview {
    fun byId(
        bookId: BookId,
        reviewId: ReviewId,
    ): Review

    fun all(bookId: BookId): List<Review>
}
