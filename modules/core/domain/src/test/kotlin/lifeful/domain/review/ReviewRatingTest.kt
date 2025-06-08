package lifeful.domain.review

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.blocking.forAll
import io.kotest.data.row
import lifefule.review.domain.ReviewRating

class ReviewRatingTest : StringSpec({

    "0.5 이상 그리고 0.5 단위가 아닌 평점은 예외를 던진다" {
        forAll(
            row(-0.5),
            row(0.0),
            row(0.1),
            row(0.4),
            row(0.6),
            row(1.3),
            row(4.7),
            row(5.1),
        ) { value ->
            shouldThrowAny {
                ReviewRating(value)
            }
        }
    }

    "0.5 단위의 평점은 정상 생성된다" {
        forAll(
            row(0.5),
            row(1.0),
            row(2.5),
            row(4.0),
            row(5.0),
        ) { valid ->
            shouldNotThrowAny {
                ReviewRating(valid)
            }
        }
    }
})
