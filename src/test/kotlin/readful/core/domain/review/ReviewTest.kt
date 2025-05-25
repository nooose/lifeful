package readful.core.domain.review

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import readful.core.domain.shared.BookId
import readful.core.domain.shared.ReviewerId

class ReviewTest : StringSpec({

    "후기 작성자가 다르면 예외를 던진다." {
        val reviewerId = ReviewerId(1L)

        val review = Review(
            rating = ReviewRating(5.0),
            comment = "후기 내용",
            reviewerId = reviewerId,
            bookId = BookId(1L),
        )

        val otherReviewer = ReviewerId(2L)

        shouldThrow<ReviewerMismatchException> {
            review.edit("후기 내용", otherReviewer)
        }
    }
})
