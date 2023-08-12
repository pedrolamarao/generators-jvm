import me.champeau.jmh.JMHPlugin
import me.champeau.jmh.JmhParameters

plugins {
    id("base")
    id("me.champeau.jmh") apply(false)
}

allprojects {
    group = "br.dev.pedrolamarao.generators"
    version = "1.0-SNAPSHOT"
}

subprojects {
    plugins.withType<JavaPlugin>().configureEach {
        extensions.configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set( JavaLanguageVersion.of(21) )
            }
        }
        tasks.withType<Test>().configureEach {
            jvmArgs = listOf("--add-exports","java.base/jdk.internal.vm=ALL-UNNAMED","--enable-preview")
        }
    }
    plugins.withType<JMHPlugin>().configureEach {
        extensions.configure<JmhParameters> {
            fork.set(1)
            iterations.set(1)
            jvmArgs.set( listOf("--add-exports","java.base/jdk.internal.vm=ALL-UNNAMED","--enable-preview") )
            warmupIterations.set(1)
        }
    }
}

tasks.wrapper.configure {
    gradleVersion = "8.2.1"
}