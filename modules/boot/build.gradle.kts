tasks.named("bootJar") {
    enabled = true
}

tasks.named("jar") {
    enabled = false
}

dependencies {
    api(project(":modules:book:api"))
    api(project(":modules:book:data:db"))
    api(project(":modules:review:api"))
    api(project(":modules:review:data:db"))
    runtimeOnly("com.h2database:h2")
}
