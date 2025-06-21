package lifeful.book.data

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime
import lifeful.book.domain.Book
import lifeful.shared.BaseModel
import lifeful.shared.BookId

/**
 * 책 엔티티
 * @author hd15807@gmail.com
 */
@Entity
class BookEntity(
    val isbn: String,
    val title: String,
    val description: String,
    val author: String,
    val publisher: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: BookId = BookId(),
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val modifiedAt: LocalDateTime = LocalDateTime.now(),
) : BaseModel {
    fun toDomain(): Book {
        return Book(
            isbn = isbn,
            title = title,
            description = description,
            author = author,
            publisher = publisher,
            id = id,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
    }
}
