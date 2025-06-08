package lifeful.support

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

@SecurityScheme(
    name = OpenApiConfig.Companion.BEARER_AUTH,
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
)
@OpenAPIDefinition(
    info =
        Info(
            title = "Lifeful API",
            description = "기록 기반 플랫폼 REST API 문서",
            version = "v0.0.1",
        ),
)
@Configuration(proxyBeanMethods = false)
class OpenApiConfig {
    companion object {
        const val BEARER_AUTH = "bearerAuth"
    }
}
