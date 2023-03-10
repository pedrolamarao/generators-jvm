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
                languageVersion.set( JavaLanguageVersion.of(19) )
            }
        }
        tasks.withType<Test>().configureEach {
            jvmArgs = listOf("--add-exports","java.base/jdk.internal.vm=br.dev.pedrolamarao.generators,ALL-UNNAMED","--enable-preview")
        }
    }
}
