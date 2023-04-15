plugins {
    id("java-library")
    id("me.champeau.jmh")
}

dependencies {
    implementation(project(":generators"))
    implementation(project(":readers:line"))
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
