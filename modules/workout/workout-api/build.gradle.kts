dependencies {
    implementation(project(":modules:workout:core:workout-application"))
    implementation(project(":modules:workout:core:workout-domain"))

    integrationTestRuntimeOnly("com.h2database:h2")
}
