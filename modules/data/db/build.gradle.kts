plugins {
    kotlin("plugin.jpa")
}

dependencies {
    implementation(project(":modules:core:domain"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.modulith:spring-modulith-starter-jpa")
    runtimeOnly("com.h2database:h2")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}
