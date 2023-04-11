plugins {
    id("java-library")
    id("me.champeau.jmh")
}

dependencies {
    implementation(project(":generators"))
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

jmh {
    jvmArgs.set( listOf("--add-exports","java.base/jdk.internal.vm=ALL-UNNAMED","--enable-preview") )
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}