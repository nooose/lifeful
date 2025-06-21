package lifeful.book.data

import lifeful.book.domain.Book
import lifeful.book.domain.BookRepository
import org.springframework.stereotype.Repository

@Repository
internal class BookRepositoryImpl(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {
    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
            .map { it.toDomain() }
    }
}
