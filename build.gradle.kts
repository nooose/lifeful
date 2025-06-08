plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.5.0"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

allprojects {
    group = "lifeful"
    version = "0.0.1-SNAPSHOT"

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
        val springModulithVersion = "1.3.5"
        imports {
            mavenBom("org.springframework.modulith:spring-modulith-bom:$springModulithVersion")
        }
    }

    dependencies {
        implementation("org.springframework.modulith:spring-modulith-starter-core")
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

ktlint {
    android.set(false)
    outputToConsole.set(true)
    enableExperimentalRules.set(false)
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
