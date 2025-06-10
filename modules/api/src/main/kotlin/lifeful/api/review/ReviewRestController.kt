package lifeful.api.review

import java.net.URI
import lifeful.review.application.AddReview
import lifeful.review.application.FindReview
import lifeful.review.application.ModifyReview
import lifeful.security.currentMemberId
import lifefule.shared.BookId
import lifefule.shared.ReviewId
import lifefule.shared.ReviewerId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/v1")
@RestController
class ReviewRestController(
    private val addReview: AddReview,
    private val findReview: FindReview,
    private val modifyReview: ModifyReview,
) : ReviewApi {
    @PostMapping("/books/{bookId}/reviews")
    override fun review(
        @PathVariable bookId: BookId,
        @RequestBody request: ReviewAddRequest,
    ): ResponseEntity<Unit> {
        val review =
            request.toDomain(
                bookId = bookId,
                reviewerId = ReviewerId(currentMemberId()),
            )

        addReview.add(review)

        val location = URI.create("/reviews/${review.id}")
        return ResponseEntity.created(location).build()
    }

    @GetMapping("/books/{bookId}/reviews")
    override fun getReviews(
        @PathVariable bookId: BookId,
    ): List<ReviewSummaryResponse> {
        val reviews = findReview.all(bookId = bookId)
        return reviews.map { ReviewSummaryResponse.from(it) }
    }

    @GetMapping("/books/{bookId}/reviews/{reviewId}")
    override fun getReview(
        @PathVariable bookId: BookId,
        @PathVariable reviewId: ReviewId,
    ): ReviewResponse {
        val review = findReview.byId(bookId = bookId, reviewId = reviewId)
        return ReviewResponse.from(review)
    }

    @PatchMapping("/books/{bookId}/reviews/{reviewId}")
    override fun editReview(
        @PathVariable bookId: BookId,
        @PathVariable reviewId: ReviewId,
        @RequestBody request: ReviewEditRequest,
    ) {
        modifyReview.edit(
            bookId = bookId,
            reviewId = reviewId,
            reviewerId = ReviewerId(currentMemberId()),
            rating = request.rating,
            comment = request.comment,
        )
    }
}
