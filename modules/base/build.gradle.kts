dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation(project(":modules:support:redis"))
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.springframework:spring-tx")
    testFixturesImplementation("org.springframework.boot:spring-boot-starter-web")
}
