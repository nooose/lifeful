package lifeful.api.book

import io.swagger.v3.oas.annotations.media.Schema
import lifefule.book.domain.Book
import lifefule.shared.BookId

@Schema(description = "책 응답")
data class BookResponse(
    @field:Schema(description = "책 식별자")
    val id: BookId,
    @field:Schema(description = "ISBN")
    val isbn: String,
    @field:Schema(description = "제목")
    val title: String,
    @field:Schema(description = "저자")
    val author: String,
    @field:Schema(description = "설명")
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
