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

jmh {
    fork.set(1)
    iterations.set(1)
    jmhVersion.set("1.36")
    jvmArgs.set( listOf("--add-exports","java.base/jdk.internal.vm=ALL-UNNAMED","--enable-preview") )
    timeUnit.set("ms")
    warmupIterations.set(1)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
