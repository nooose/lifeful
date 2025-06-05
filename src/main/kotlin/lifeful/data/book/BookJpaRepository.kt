package lifeful.data.book

import org.springframework.data.jpa.repository.JpaRepository
import lifeful.core.domain.book.Book
import lifeful.core.domain.shared.BookId

internal interface BookJpaRepository : JpaRepository<Book, BookId> {
}
