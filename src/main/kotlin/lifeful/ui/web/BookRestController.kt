package lifeful.ui.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import lifeful.core.application.book.query.FindBook
import lifeful.core.domain.shared.BookId

@RestController
internal class BookRestController(
    private val findBook: FindBook
) {

    @GetMapping("/books")
    fun getBooks(): List<BookResponse> {
        val books = findBook.all()
        return books.map { BookResponse.from(it) }
    }

    @PostMapping("/books")
    fun addBook() {
        TODO("책 추가 구현")
    }

    @GetMapping("/books/{bookId}")
    fun getBook(
        @PathVariable bookId: BookId,
    ) {
        TODO("책 상세 조회 구현")
    }
}
