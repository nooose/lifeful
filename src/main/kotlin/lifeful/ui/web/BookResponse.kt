package lifeful.ui.web

import lifeful.core.domain.book.Book
import lifeful.core.domain.shared.BookId

internal data class BookResponse(
    val id: BookId,
    val isbn: String,
    val title: String,
    val author: String,
    val description: String,
) {

    companion object {
        fun from(domain: Book): BookResponse {
            return BookResponse(
                id = domain.id,
                isbn = domain.isbn,
                title = domain.title,
                author = domain.author,
                description = domain.description,
            )
        }
    }
}
