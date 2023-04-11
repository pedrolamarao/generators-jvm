plugins {
    id("java-library")
    id("me.champeau.jmh")
}

dependencies {
    implementation(project(":generators"))
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    jmh("org.bouncycastle:bcprov-jdk18on:1.72")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
