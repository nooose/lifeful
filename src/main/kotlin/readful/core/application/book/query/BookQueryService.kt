package readful.core.application.book.query

import org.springframework.stereotype.Service
import readful.core.domain.book.Book
import readful.core.domain.book.BookClient

@Service
class BookQueryService(
    private val bookClient: BookClient,
): FindBook {

    override fun all(title: String): List<Book> {
        return bookClient.getBook(title) ?: emptyList()
    }
}
