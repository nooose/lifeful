package lifeful.api.book

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.support.RequiredAuthorization
import lifefule.shared.BookId
import org.springframework.web.bind.annotation.PathVariable

@RequiredAuthorization
@Tag(
    name = "도서 API",
    description = "도서 관련 API",
)
interface BookApi {
    @Operation(
        summary = "책 목록 조회",
        operationId = "getBooks",
    )
    fun getBooks(): List<BookResponse>

    @Operation(
        summary = "책 등록",
        operationId = "addBook",
    )
    fun addBook()

    @Operation(
        summary = "책 상세 조회",
        operationId = "getBook",
    )
    fun getBook(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
    ): BookResponse
}
