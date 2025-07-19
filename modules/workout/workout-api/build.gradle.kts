dependencies {
    implementation(project(":modules:workout:core:workout-application"))
    implementation(project(":modules:workout:core:workout-domain"))

    integrationTestImplementation(project(":modules:workout:workout-jpa"))
    integrationTestRuntimeOnly("com.h2database:h2")
}
