package readful.data.book.client.aladin

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import readful.core.domain.book.Book
import readful.core.domain.book.BookClient
import readful.data.book.AlaldinApiProperties

/**
 * 알라딘 오픈 API 사용해서 책 목록 조회
 * @author kgu1060@gmail.com
 */
@Component
class AladinClient(
    @Qualifier("aladinWebClient")
    private val webClient: WebClient,
    private val aladinApiProperties: AlaldinApiProperties
) : BookClient {

    override fun getBook(title: String): List<Book>? {
        val request = AladinBookRequest(
            ttbkey = aladinApiProperties.clientId,
            query = title,
        )

        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .scheme("http")
                    .host("www.aladin.co.kr")
                    .path("/ItemSearch.aspx")
                    .queryParam("ttbkey", request.ttbkey)
                    .queryParam("Query", request.query)
                    .queryParam("MaxResults", request.maxResults)
                    .queryParam("start", request.start)
                    .queryParam("output", request.output)
                    .queryParam("Version", request.version)
                    .build()
            }
            .retrieve()
            .bodyToMono(AladinBookSearchResponse::class.java)
            .block()!!

        return response.item.map { it.toDomain() }
    }
}
