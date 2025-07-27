dependencies {
    implementation(project(":modules:member:core:member-application"))
    implementation(project(":modules:member:core:member-domain"))

    testImplementation(testFixtures(project(":modules:member:core:member-domain")))
}
