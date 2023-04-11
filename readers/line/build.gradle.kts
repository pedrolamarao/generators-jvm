plugins {
    id("java-library")
    id("me.champeau.jmh")
}

dependencies {
    implementation(project(":generators"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
