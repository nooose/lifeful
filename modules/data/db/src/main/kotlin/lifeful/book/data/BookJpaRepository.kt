package lifeful.book.data

import lifeful.shared.BookId
import org.springframework.data.jpa.repository.JpaRepository

internal interface BookJpaRepository : JpaRepository<BookEntity, BookId>
