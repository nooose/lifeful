package lifeful.data.book

import org.springframework.stereotype.Repository
import lifeful.core.domain.book.Book
import lifeful.core.domain.book.BookRepository

@Repository
internal class BookRdbRepository(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {

    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
    }
}
