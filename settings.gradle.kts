dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

rootProject.name = "generators-jvm"

include("generators")
include("readers:csv")
