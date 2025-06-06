package lifeful.data.book

import lifeful.core.domain.book.Book
import lifeful.core.domain.book.BookRepository
import org.springframework.stereotype.Repository

@Repository
internal class BookRdbRepository(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {
    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
    }
}
