package readful.data.book

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "readful.book.naver")
data class NaverApiProperties(
    val clientId: String,
    val clientSecret: String
)

@ConfigurationProperties(prefix = "readful.book.aladin")
data class AlaldinApiProperties(
    val clientId: String,
)
