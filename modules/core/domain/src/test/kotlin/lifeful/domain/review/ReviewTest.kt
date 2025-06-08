package lifeful.domain.review

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import lifefule.domain.review.Review
import lifefule.domain.review.ReviewRating
import lifefule.domain.review.ReviewerMismatchException
import lifefule.domain.shared.BookId
import lifefule.domain.shared.ReviewerId

class ReviewTest : StringSpec({

    "후기 작성자가 다르면 예외를 던진다." {
        val reviewerId = ReviewerId(1L)

        val review =
            Review(
                rating = ReviewRating.FIVE_STAR,
                comment = "후기 내용",
                reviewerId = reviewerId,
                bookId = BookId(1L),
            )

        val otherReviewer = ReviewerId(2L)

        shouldThrow<ReviewerMismatchException> {
            review.edit(
                rating = ReviewRating.FIVE_STAR,
                comment = "후기 내용",
                otherReviewer,
            )
        }
    }
})
