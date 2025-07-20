tasks.named("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":modules:member:member-api"))
    implementation(project(":modules:workout:workout-api"))
    implementation(project(":modules:security"))
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("com.mysql:mysql-connector-j")
}
