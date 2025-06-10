package lifeful.api.review

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import lifefule.shared.BookId
import lifefule.shared.ReviewId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(
    name = "후기 API",
    description = "후기 관련 API",
)
interface ReviewApi {
    @Operation(
        summary = "후기 등록",
        operationId = "review",
    )
    fun review(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
        @RequestBody request: ReviewAddRequest,
    ): ResponseEntity<Unit>

    @Operation(
        summary = "후기 목록 조회",
        operationId = "getReviews",
    )
    fun getReviews(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
    ): List<ReviewSummaryResponse>

    @Operation(
        summary = "후기 상세 조회",
        operationId = "getReview",
    )
    fun getReview(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
        @Parameter(description = "후기 식별자")
        @PathVariable reviewId: ReviewId,
    ): ReviewResponse

    @Operation(
        summary = "후기 수정",
        operationId = "editReview",
    )
    fun editReview(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
        @Parameter(description = "후기 식별자")
        @PathVariable reviewId: ReviewId,
        @RequestBody request: ReviewEditRequest,
    )
}
