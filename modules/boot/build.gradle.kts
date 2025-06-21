tasks.named("bootJar") {
    enabled = true
}

tasks.named("jar") {
    enabled = false
}

dependencies {
    implementation(project(":modules:book:book-api"))
    implementation(project(":modules:book:book-data:book-db"))
    implementation(project(":modules:review:review-api"))
    implementation(project(":modules:review:review-data:review-db"))
    implementation(project(":modules:member:member-api"))
}
