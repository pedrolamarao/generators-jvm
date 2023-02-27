plugins {
    id("java-library")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs = listOf("--add-exports","java.base/jdk.internal.vm=br.dev.pedrolamarao.generators","--enable-preview")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
