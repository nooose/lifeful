dependencies {
    implementation(project(":modules:health:core:health-domain"))
    implementation(project(":modules:member:core:member-event"))
    testImplementation(testFixtures(project(":modules:health:core:health-domain")))
}
