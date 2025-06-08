package lifeful.data.book

import lifefule.domain.book.Book
import lifefule.domain.book.BookRepository
import org.springframework.stereotype.Repository

@Repository
internal class BookRdbRepository(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {
    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
    }
}
