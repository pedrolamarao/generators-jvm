plugins {
    id("base")
}

allprojects {
    group = "br.dev.pedrolamarao.generators"
    version = "1.0-SNAPSHOT"
}

subprojects {
    plugins.withType<JavaPlugin>().configureEach {
        extensions.getByType<JavaPluginExtension>().apply {
            toolchain {
                languageVersion.set( JavaLanguageVersion.of(20) )
            }
        }
        tasks.withType<JavaCompile>().configureEach {
            options.compilerArgs = listOf("--add-exports","java.base/jdk.internal.vm=br.dev.pedrolamarao.generators","--enable-preview")
        }
        tasks.withType<Test>().configureEach {
            jvmArgs = listOf("--add-exports","java.base/jdk.internal.vm=br.dev.pedrolamarao.generators","--enable-preview")
        }
    }
}
