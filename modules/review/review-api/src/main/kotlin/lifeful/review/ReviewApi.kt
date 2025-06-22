package lifeful.review

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.shared.BookId
import lifeful.shared.ReviewId
import lifeful.support.RequiredAuthorization
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@RequiredAuthorization
@Tag(
    name = "후기 API",
    description = "후기 관련 API",
)
internal interface ReviewApi {
    @Operation(
        summary = "후기 등록",
        operationId = "review",
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "리뷰 작성 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "401", description = "인증 필요"),
        ],
    )
    fun review(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
        @RequestBody request: ReviewAddRequest,
        authentication: Authentication,
    ): ResponseEntity<Unit>

    @Operation(
        summary = "후기 목록 조회",
        operationId = "getReviews",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "리뷰 목록 조회 성공",
                content = [
                    Content(
                        mediaType = "application/json",
                        array = ArraySchema(schema = Schema(implementation = ReviewSummaryResponse::class)),
                    ),
                ],
            ),
        ],
    )
    fun getReviews(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
    ): List<ReviewSummaryResponse>

    @Operation(
        summary = "후기 상세 조회",
        operationId = "getReview",
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "리뷰 조회 성공",
                content = [Content(mediaType = "application/json", schema = Schema(implementation = ReviewResponse::class))],
            ),
            ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음"),
        ],
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
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "리뷰 수정 성공"),
            ApiResponse(responseCode = "400", description = "잘못된 요청"),
            ApiResponse(responseCode = "401", description = "인증 필요"),
            ApiResponse(responseCode = "403", description = "권한 없음"),
            ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음"),
        ],
    )
    @RequiredAuthorization
    fun editReview(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
        @Parameter(description = "후기 식별자")
        @PathVariable reviewId: ReviewId,
        @RequestBody request: ReviewEditRequest,
        authentication: Authentication,
    )
}
