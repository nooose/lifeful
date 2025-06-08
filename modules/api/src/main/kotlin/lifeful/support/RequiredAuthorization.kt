package lifeful.support

import io.swagger.v3.oas.annotations.security.SecurityRequirement

@SecurityRequirement(name = OpenApiConfig.Companion.BEARER_AUTH)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequiredAuthorization
