package readful.core.domain.book

import readful.core.domain.shared.BookId

/**
 * 책 저장소
 *
 * @author hd15807@gmail.com
 */
interface BookRepository {

    fun findAll(): List<Book>
    fun findById(id: BookId): Book?
}
