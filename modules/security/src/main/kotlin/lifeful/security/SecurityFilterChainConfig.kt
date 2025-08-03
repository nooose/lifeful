package lifeful.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher.withDefaults
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.security.web.util.matcher.RequestMatchers

@Configuration(proxyBeanMethods = false)
internal class SecurityFilterChainConfig(
    private val jwtValidator: JwtValidator,
    private val customEntryPoint: AuthenticationEntryPoint,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            formLogin { disable() }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            logout {
                disable()
            }
            exceptionHandling {
                authenticationEntryPoint = customEntryPoint
                accessDeniedHandler = customAccessDeniedHandler
            }
            authorizeHttpRequests {
                authorize(PERMIT_REQUESTS, permitAll)
                authorize("/api/v1/**", authenticated)
            }
            addFilterBefore<AuthorizationFilter>(JwtFilter(PERMIT_REQUESTS, jwtValidator))
        }
        return http.build()
    }

    companion object {
        val PERMIT_REQUESTS: RequestMatcher = RequestMatchers.anyOf(
            withDefaults().matcher(POST, "/api/v1/members"),
            withDefaults().matcher(POST, "/api/v1/auth/token"),
            withDefaults().matcher(GET, "/actuator/health/**"),
            withDefaults().matcher(GET, "/swagger-ui.html"),
            withDefaults().matcher(GET, "/swagger-ui/**"),
            withDefaults().matcher(GET, "/v3/api-docs/**"),
            withDefaults().matcher(GET, "/favicon.ico"),
            withDefaults().matcher(GET, "/.well-known/**"),
            withDefaults().matcher(GET, "/error"),
        )
    }
}
