tasks.withType<Jar> {
    archiveBaseName.set("review-api")
}

dependencies {
    implementation(project(":modules:review:core:application"))
    implementation(project(":modules:review:core:domain"))
    implementation(project(":modules:security"))
}
