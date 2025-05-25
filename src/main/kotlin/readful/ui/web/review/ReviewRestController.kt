package readful.ui.web.review

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import readful.core.application.review.AddReview
import readful.core.application.review.FindReview
import readful.core.application.review.ModifyReview
import readful.core.domain.shared.BookId
import readful.core.domain.shared.ReviewId
import readful.core.domain.shared.ReviewerId
import readful.security.currentMemberId
import java.net.URI

@RestController
class ReviewRestController(
    private val addReview: AddReview,
    private val findReview: FindReview,
    private val modifyReview: ModifyReview,
) {

    @PostMapping("/books/{bookId}/reviews")
    fun review(
        @PathVariable bookId: BookId,
        @RequestBody request: ReviewAddRequest,
    ): ResponseEntity<Unit> {
        val review = request.toDomain(
            bookId = bookId,
            reviewerId = ReviewerId(currentMemberId()),
        )

        addReview.add(review)

        val location = URI.create("/reviews/${review.id}")
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/books/{bookId}/reviews")
    fun getReviews(
        @PathVariable bookId: BookId,
    ): List<ReviewSummaryResponse> {
        val reviews = findReview.all(bookId = bookId)
        return reviews.map {ReviewSummaryResponse.from(it)}
    }

    @GetMapping("/reviews/{reviewId}")
    fun getReview(
        @PathVariable reviewId: ReviewId,
    ): ReviewResponse {
        val review = findReview.byId(reviewId)
        return ReviewResponse.from(review)
    }

    @PatchMapping("/reviews/{reviewId}")
    fun editReview(
        @PathVariable reviewId: ReviewId,
        @RequestBody request: ReviewEditRequest,
    ) {
        modifyReview.edit(
            reviewId = reviewId,
            reviewerId = ReviewerId(currentMemberId()),
            rating = request.rating,
            comment = request.comment,
        )
    }
}
