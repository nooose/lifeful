import com.linecorp.support.project.multi.recipe.configureByTypeHaving
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25" apply false
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "1.9.25" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.3.0"
    id("com.linecorp.build-recipe-plugin") version "1.1.1"
    id("java-test-fixtures")
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
    apply(plugin = "java-test-fixtures")

    tasks.named("bootJar") {
        enabled = false
    }

    tasks.named("jar") {
        enabled = true
    }

    val dependencyGroups = mapOf(
        "org.springdoc" to "2.8.9",
        "io.kotest" to "5.9.0",
        "io.kotest.extensions" to "1.3.0",
        "io.mockk" to "1.14.2",
        "com.ninja-squad" to "4.0.2",
        "io.github.oshai" to "7.0.7",
        "io.jsonwebtoken" to "0.12.5",
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
    }

    testing {
        suites {
            val test by getting(JvmTestSuite::class)
            val integrationTest by registering(JvmTestSuite::class)

            withType<JvmTestSuite> {
                useJUnitJupiter()

                targets {
                    all {
                        dependencies {
                            implementation(project())
                            implementation(testFixtures(project(":modules:base")))
                            implementation("io.mockk:mockk")
                            implementation("com.ninja-squad:springmockk")
                            implementation("io.kotest:kotest-runner-junit5")
                            implementation("io.kotest.extensions:kotest-extensions-spring")
                            runtimeOnly("org.junit.platform:junit-platform-launcher")
                            runtimeOnly("com.h2database:h2")
                        }
                        testTask.configure {
                            shouldRunAfter(test)
                            testLogging {
                                events = mutableSetOf(TestLogEvent.PASSED, TestLogEvent.FAILED)
                                exceptionFormat = TestExceptionFormat.FULL
                            }
                        }
                    }
                }
            }
        }
    }

    val integrationTestImplementation by configurations.getting {
        extendsFrom(configurations.testImplementation.get())
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
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.modulith:spring-modulith-starter-core")
        implementation("org.springframework.modulith:spring-modulith-starter-jpa")
        runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
        runtimeOnly("org.springframework.modulith:spring-modulith-observability")
        implementation("io.micrometer:micrometer-registry-otlp")

        testImplementation("org.springframework.modulith:spring-modulith-starter-test")
    }
}

configureByTypeHaving("application") {
    dependencies {
        implementation("org.springframework.modulith:spring-modulith-starter-core")
        implementation("org.springframework.modulith:spring-modulith-events-api")
        implementation("org.springframework.modulith:spring-modulith-events-core")
        implementation("org.springframework.data:spring-data-commons")
    }
}

configureByTypeHaving("integration") {
    dependencies {
        implementation("org.springframework.data:spring-data-commons")
        implementation("org.springframework:spring-tx")
        implementation("org.springframework:spring-context")
    }
}

configureByTypeHaving("mvc") {
    dependencies {
        implementation(project(":modules:support:api-doc"))
        implementation(project(":modules:support:api-common"))
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.data:spring-data-commons")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.springframework.security:spring-security-core")
    }
}

configureByTypeHaving("jpa") {
    apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    }
}
