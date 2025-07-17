tasks.named("bootJar") {
    enabled = true
}

dependencies {
    implementation(project(":modules:member:member-api"))
    implementation(project(":modules:workout:workout-api"))
    implementation(project(":modules:workout:workout-jpa"))
    implementation(project(":modules:security"))
    runtimeOnly("com.h2database:h2")
}
