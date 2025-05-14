package readful.data.book

import org.springframework.data.jpa.repository.JpaRepository
import readful.core.domain.book.Book
import readful.core.domain.shared.BookId

internal interface BookJpaRepository : JpaRepository<Book, BookId> {
}
