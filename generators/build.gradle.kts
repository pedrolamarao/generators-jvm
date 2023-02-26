plugins {
    id("java-library")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
