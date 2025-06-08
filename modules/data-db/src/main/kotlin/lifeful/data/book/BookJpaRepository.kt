package lifeful.data.book

import lifefule.domain.book.Book
import lifefule.domain.shared.BookId
import org.springframework.data.jpa.repository.JpaRepository

internal interface BookJpaRepository : JpaRepository<Book, BookId>
