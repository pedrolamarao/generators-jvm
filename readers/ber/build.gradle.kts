plugins {
    id("java-library")
    id("me.champeau.jmh")
}

dependencies {
    implementation(project(":generators"))
    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

jmh {
    jmhVersion.set("1.36")
    jvmArgs.set( listOf("--add-exports","java.base/jdk.internal.vm=ALL-UNNAMED","--enable-preview") )
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
