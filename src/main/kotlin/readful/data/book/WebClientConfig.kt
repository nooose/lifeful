package readful.data.book

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun aladinWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://www.aladin.co.kr/ttb/api")
            .build()
    }
}
