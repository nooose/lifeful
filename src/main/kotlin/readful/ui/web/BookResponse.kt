package readful.ui.web

import readful.core.domain.book.Book
import readful.core.domain.shared.BookId

internal data class BookResponse(
    val id: BookId,
    val isbn: String,
    val title: String,
    val author: String,
    val description: String,
    val chapters: List<BookChapterResponse>,
) {

    companion object {
        fun from(domain: Book): BookResponse {
            return BookResponse(
                id = domain.id,
                isbn = domain.isbn,
                title = domain.title,
                author = domain.author,
                description = domain.description,
                chapters = domain.chapters.map { BookChapterResponse.from(it) }
            )
        }
    }
}
