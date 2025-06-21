package lifeful.book

import org.springframework.modulith.NamedInterface

/**
 * 책을 검색하는 유즈케이스이다.
 *
 * @author hd15807@gmail.com
 */
@NamedInterface("find-book")
interface FindBook {
    fun all(): List<Book>
}
