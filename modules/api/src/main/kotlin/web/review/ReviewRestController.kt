package web.review

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
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

@Tag(
    name = "후기 API",
    description = "후기 관련 API",
)
@RequestMapping("/api/v1")
@RestController
class ReviewRestController(
    private val addReview: AddReview,
    private val findReview: FindReview,
    private val modifyReview: ModifyReview,
) {
    @Operation(
        summary = "후기 등록",
        operationId = "review",
    )
    @PostMapping("/books/{bookId}/reviews")
    fun review(
        @Parameter(description = "책 식별자")
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

    @Operation(
        summary = "후기 목록 조회",
        operationId = "getReviews",
    )
    @GetMapping("/books/{bookId}/reviews")
    fun getReviews(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
    ): List<ReviewSummaryResponse> {
        val reviews = findReview.all(bookId = bookId)
        return reviews.map { ReviewSummaryResponse.from(it) }
    }

    @Operation(
        summary = "후기 상세 조회",
        operationId = "getReview",
    )
    @GetMapping("/books/{bookId}/reviews/{reviewId}")
    fun getReview(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
        @Parameter(description = "후기 식별자")
        @PathVariable reviewId: ReviewId,
    ): ReviewResponse {
        val review = findReview.byId(bookId = bookId, reviewId = reviewId)
        return ReviewResponse.from(review)
    }

    @Operation(
        summary = "후기 수정",
        operationId = "editReview",
    )
    @PatchMapping("/books/{bookId}/reviews/{reviewId}")
    fun editReview(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
        @Parameter(description = "후기 식별자")
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
