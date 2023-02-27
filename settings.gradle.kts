pluginManagement {
    plugins {
        id("me.champeau.jmh") version "0.7.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "generators-jvm"

include("generators")
include("readers:csv")
