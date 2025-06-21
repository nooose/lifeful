import com.linecorp.support.project.multi.recipe.configureByTypeHaving

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
    id("com.linecorp.build-recipe-plugin") version "1.1.1"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allprojects {
    group = "lifeful"
    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    tasks.named("bootJar") {
        enabled = false
    }

    tasks.named("jar") {
        enabled = true
    }

    val dependencyGroups = mapOf(
        "org.springdoc" to "2.8.8",
        "io.kotest" to "5.9.0",
        "io.github.oshai" to "7.0.7",
    )

    configurations.all {
        resolutionStrategy.eachDependency {
            dependencyGroups[requested.group]?.also { constraintVersion ->
                useVersion(constraintVersion)
                because("custom dependency group")
            }
        }
    }

    dependencyManagement {
        val springModulithVersion = "1.4.1"
        imports {
            mavenBom("org.springframework.modulith:spring-modulith-bom:$springModulithVersion")
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("io.github.oshai:kotlin-logging-jvm")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.kotest:kotest-runner-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

configureByTypeHaving("kotlin") {
    dependencies {
        implementation(project(":modules:base"))
    }
}

configureByTypeHaving("boot") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")
        implementation("org.springframework.modulith:spring-modulith-starter-core")
        runtimeOnly("org.springframework.modulith:spring-modulith-runtime")

        testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    }
}

configureByTypeHaving("application") {
    dependencies {
        implementation("org.springframework.modulith:spring-modulith-starter-core")
        implementation("org.springframework.modulith:spring-modulith-events-api")
        implementation("org.springframework:spring-tx")
    }
}

configureByTypeHaving("api") {
    dependencies {
        implementation(project(":modules:support:api-doc"))
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
        runtimeOnly("org.springframework.modulith:spring-modulith-observability")
    }
}

configureByTypeHaving("db") {
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.modulith:spring-modulith-starter-jpa")
        runtimeOnly("com.h2database:h2")
    }
}
