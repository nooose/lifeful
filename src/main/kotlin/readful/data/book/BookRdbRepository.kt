package readful.data.book

import org.springframework.stereotype.Repository
import readful.core.domain.book.Book
import readful.core.domain.book.BookRepository

@Repository
internal class BookRdbRepository(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {

    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
    }
}
