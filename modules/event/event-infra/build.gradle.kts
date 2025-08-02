dependencies {
    implementation("org.springframework.modulith:spring-modulith-starter-core")
    implementation("org.springframework.modulith:spring-modulith-events-core")
    implementation(project(":modules:event:core:event-domain"))
}
