package lifeful.member

import lifeful.shared.id.MemberId
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.util.Date

@TestConfiguration
class MemberTestConfiguration {
    @Bean
    fun noOpTokenGenerator(): MemberTokenGenerator {
        return object : MemberTokenGenerator {
            override fun generate(
                memberId: MemberId,
                issuedAt: Date,
            ): String = "token"
        }
    }

    @Bean
    fun noOpPasswordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(password: String): String = "${password}Hash"

            override fun matches(
                rawPassword: String,
                encodedPassword: String,
            ): Boolean = "${rawPassword}Hash" == encodedPassword
        }
    }
}
