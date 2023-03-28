pluginManagement {
    plugins {
        id("me.champeau.jmh") version "0.7.0"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.4.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "generators-jvm"

include("generators")
include("readers:ber")
include("readers:csv")
include("readers:line")
include("readers:rsa")