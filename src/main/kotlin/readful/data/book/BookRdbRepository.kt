package readful.data.book

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import readful.core.domain.book.Book
import readful.core.domain.book.BookRepository
import readful.core.domain.shared.BookId

@Repository
internal class BookRdbRepository(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {

    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
    }

    override fun findById(id: BookId): Book {
        return bookJpaRepository.findById(id).orElse(null)
    }

}
