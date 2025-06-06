package lifeful.ui.web.book

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import lifeful.core.application.book.query.FindBook
import lifeful.core.domain.shared.BookId
import lifeful.ui.web.support.RequiredAuthorization
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RequiredAuthorization
@Tag(
    name = "도서 API",
    description = "도서 관련 API",
)
@RestController
internal class BookRestController(
    private val findBook: FindBook
) {

    @Operation(
        summary = "책 목록 조회",
        operationId = "getBooks",
    )
    @GetMapping("/books")
    fun getBooks(): List<BookResponse> {
        val books = findBook.all()
        return books.map { BookResponse.from(it) }
    }

    @Operation(
        summary = "책 등록",
        operationId = "addBook",
    )
    @PostMapping("/books")
    fun addBook() {
        TODO("책 추가 구현")
    }

    @Operation(
        summary = "책 상세 조회",
        operationId = "getBook",
    )
    @GetMapping("/books/{bookId}")
    fun getBook(
        @Parameter(description = "책 식별자")
        @PathVariable bookId: BookId,
    ): BookResponse {
        TODO("책 상세 조회 구현")
    }
}
