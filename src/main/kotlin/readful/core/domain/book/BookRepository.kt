package readful.core.domain.book

/**
 * 책 저장소
 *
 * @author hd15807@gmail.com
 */
interface BookRepository {

    fun findAll(): List<Book>
}
