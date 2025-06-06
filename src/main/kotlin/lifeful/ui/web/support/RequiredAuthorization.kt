package lifeful.ui.web.support

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import lifeful.ui.web.support.OpenApiConfig.Companion.BEARER_AUTH

@SecurityRequirement(name = BEARER_AUTH)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiredAuthorization
