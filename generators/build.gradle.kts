plugins {
    id("java-library")
    id("me.champeau.jmh")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

tasks.compileJava.configure {
    options.compilerArgs = listOf("--add-exports","java.base/jdk.internal.vm=br.dev.pedrolamarao.generators","--enable-preview")
}

jmh {
    timeUnit = "ns"
}

tasks.test.configure {
    useJUnitPlatform()
}
