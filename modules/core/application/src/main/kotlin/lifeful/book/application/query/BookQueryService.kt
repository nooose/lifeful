package lifeful.book.application.query

import lifeful.book.domain.Book
import lifeful.book.domain.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 책 검색 유즈케이스의 구현체이다.
 *
 * @author hd15807@gmail.com
 */
@Transactional(readOnly = true)
@Service
class BookQueryService(
    private val bookRepository: BookRepository,
) : FindBook {
    override fun all(): List<Book> {
        return bookRepository.findAll()
    }
}
