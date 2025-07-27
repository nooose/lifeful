dependencies {
    implementation(project(":modules:member:core:member-domain"))
    implementation(project(":modules:member:member-integration"))

    testImplementation(testFixtures(project(":modules:member:core:member-domain")))
}
