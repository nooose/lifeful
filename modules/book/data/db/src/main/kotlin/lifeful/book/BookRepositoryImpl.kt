package lifeful.book

import org.springframework.stereotype.Repository

@Repository
class BookRepositoryImpl(
    private val bookJpaRepository: BookJpaRepository,
) : BookRepository {
    override fun findAll(): List<Book> {
        return bookJpaRepository.findAll()
            .map { it.toDomain() }
    }
}
