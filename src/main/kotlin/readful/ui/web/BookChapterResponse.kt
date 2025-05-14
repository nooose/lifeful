package readful.ui.web

import readful.core.domain.book.BookChapter

internal data class BookChapterResponse(
    val title: String,
    val description: String,
) {
    companion object {
        fun from(domain: BookChapter): BookChapterResponse {
            return BookChapterResponse(
                title = domain.title,
                description = domain.description,
            )
        }
    }
}
