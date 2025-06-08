package lifeful.book.data

import lifefule.book.domain.Book
import lifefule.book.domain.BookRepository
import org.springframework.stereotype.Repository

@Repository
internal class BookRdbRepository(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {
    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
    }
}
