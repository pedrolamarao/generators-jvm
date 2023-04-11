plugins {
    id("java-library")
    id("me.champeau.jmh")
}

dependencies {
    implementation(project(":generators"))
    implementation(project(":readers:ber"))
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testImplementation("org.bouncycastle:bcprov-jdk18on:1.72")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
