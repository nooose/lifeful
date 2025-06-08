dependencies {
    implementation(project(":modules:core:application"))
    implementation(project(":modules:core:domain"))
    implementation(project(":modules:security"))

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
