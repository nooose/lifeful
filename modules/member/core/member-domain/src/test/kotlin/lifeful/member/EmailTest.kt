package lifeful.member

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec

class EmailTest : StringSpec({
    "이메일 유효성 검사" {
        shouldThrow<IllegalArgumentException> {
            Email("invalid email")
        }
        shouldNotThrowAny {
            Email("test@test.com")
        }
    }
})
