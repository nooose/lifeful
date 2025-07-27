package lifeful.member

import lifeful.base.TestEvents
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class MemberTestConfiguration {
    @Bean
    fun noOpTokenGenerator(): MemberTokenGenerator {
        return TestTokenGenerator()
    }

    @Bean
    fun noOpPasswordEncoder(): PasswordEncoder {
        return TestPasswordEncoder()
    }

    @Bean
    fun testEvents(): TestEvents {
        return TestEvents()
    }
}
