package lifeful.book.data

import lifefule.book.domain.Book
import lifefule.shared.BookId
import org.springframework.data.jpa.repository.JpaRepository

internal interface BookJpaRepository : JpaRepository<Book, BookId>
