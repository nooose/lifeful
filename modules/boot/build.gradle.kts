tasks.named("bootJar") {
    enabled = true
}

tasks.named("jar") {
    enabled = false
}

dependencies {
    implementation(project(":modules:member:member-api"))
    implementation(project(":modules:workout:workout-api"))
    implementation(project(":modules:workout:data:workout-jpa"))
    implementation(project(":modules:security"))
}
