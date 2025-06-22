package lifeful.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.intercept.AuthorizationFilter
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.security.web.util.matcher.RequestMatchers

@Configuration(proxyBeanMethods = false)
internal class SecurityFilterChainConfig(
    private val jwtValidator: JwtValidator,
    private val jwtAuthenticationEntryPoint: AuthenticationEntryPoint,
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
                authenticationEntryPoint = jwtAuthenticationEntryPoint
            }
            authorizeHttpRequests {
                authorize(PERMIT_REQUESTS, permitAll)
                authorize("/api/v1/**", authenticated)
            }
            addFilterBefore<AuthorizationFilter>(JwtFilter(PERMIT_REQUESTS, jwtValidator, jwtAuthenticationEntryPoint))
        }
        return http.build()
    }

    companion object {
        val PERMIT_REQUESTS: RequestMatcher = RequestMatchers.anyOf(
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/v1/auth/token"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/error"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/actuator/health/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/.well-known/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/favicon.ico"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/swagger-ui.html"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/swagger-ui/**"),
            PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.GET, "/v3/api-docs/**"),
        )
    }
}
