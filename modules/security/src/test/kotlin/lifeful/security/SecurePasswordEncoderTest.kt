package lifeful.security

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class SecurePasswordEncoderTest : StringSpec({

    "비밀번호 인코딩 테스트" {
        val encoder = SecurePasswordEncoder()

        val rawPassword = "testPassword"
        val passwordHash = encoder.encode(rawPassword)

        encoder.matches(rawPassword, passwordHash) shouldBe true
        encoder.matches("wrongPassword", passwordHash) shouldBe false
    }
})
