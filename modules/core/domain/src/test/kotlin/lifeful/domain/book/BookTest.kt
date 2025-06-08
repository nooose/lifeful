package lifeful.domain.book

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec
import lifefule.book.domain.Book

class BookTest : StringSpec({

    "책을 생성할 수 있다." {
        shouldNotThrowAny {
            Book(
                isbn = "123456789012",
                title = "테스트 책",
                description = "테스트 설명",
                author = "김저자",
                publisher = "출판사",
            )
        }
    }
})
