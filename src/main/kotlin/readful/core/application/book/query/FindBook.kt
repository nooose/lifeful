package readful.core.application.book.query

import readful.core.domain.book.Book

/**
 * 책을 검색하는 유즈케이스이다.
 *
 * @author hd15807@gmail.com
 */
interface FindBook {

    fun all(title: String): List<Book>
}
