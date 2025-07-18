dependencies {
    implementation(project(":modules:base"))
    implementation(project(":modules:member:member-integration"))
    implementation(project(":modules:member:core:member-domain"))
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.jsonwebtoken:jjwt-api")
    implementation("io.jsonwebtoken:jjwt-impl")
    implementation("io.jsonwebtoken:jjwt-jackson")
}
