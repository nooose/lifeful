package lifeful.book

import lifeful.shared.BookId
import org.springframework.data.jpa.repository.JpaRepository

interface BookJpaRepository : JpaRepository<BookEntity, BookId>
