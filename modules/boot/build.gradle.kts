apply(plugin = "org.jlleitschuh.gradle.ktlint")

dependencies {
    implementation(project(":modules:api"))
    implementation(project(":modules:data:db"))
}
