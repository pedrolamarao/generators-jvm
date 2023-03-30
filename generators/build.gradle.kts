plugins {
    id("java-library")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.compileJava.configure {
    options.compilerArgs = listOf("--add-exports","java.base/jdk.internal.vm=br.dev.pedrolamarao.generators","--enable-preview")
}

tasks.test.configure {
    useJUnitPlatform()
}
