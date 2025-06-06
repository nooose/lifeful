package lifeful.data.book

import lifeful.core.domain.book.Book
import lifeful.core.domain.shared.BookId
import org.springframework.data.jpa.repository.JpaRepository

internal interface BookJpaRepository : JpaRepository<Book, BookId>
