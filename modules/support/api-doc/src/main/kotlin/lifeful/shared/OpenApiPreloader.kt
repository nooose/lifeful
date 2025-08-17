package lifeful.shared

import org.springdoc.core.properties.SpringDocConfigProperties
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
internal class OpenApiPreloader(
    private val springDocProperties: SpringDocConfigProperties,
    private val serverProperties: ServerProperties,
) {
    private val restClient: RestClient = RestClient.builder().build()

    @EventListener
    fun preload(event: ApplicationReadyEvent) {
        val serverPort = serverProperties.port ?: 8080
        val docPath = springDocProperties.apiDocs.path
        val openApiUrl = "http://localhost:$serverPort/$docPath"
        restClient.get()
            .uri(openApiUrl)
            .retrieve()
            .toBodilessEntity()
    }
}
