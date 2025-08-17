tasks.named("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":modules:member:member-api"))
    implementation(project(":modules:health:health-api"))
    implementation(project(":modules:health:health-infra"))
    implementation(project(":modules:admin:admin-api"))
    implementation(project(":modules:event:event-infra"))
    implementation(project(":modules:security"))

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("com.mysql:mysql-connector-j")
}

tasks.register<Copy>("copyModulithDocs") {
    description = "Spring Modulith 문서를 root에 복사합니다."
    val sourceDir = layout.buildDirectory.dir("spring-modulith-docs").get().asFile
    val destinationDir = rootProject.file("docs")

    from(sourceDir)
    into(destinationDir)
}
