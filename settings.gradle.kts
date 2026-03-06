plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0" 
    id("com.gradleup.shadow") version "9.3.2" apply false
}

rootProject.name = "mc-webapi"
include("webapi")
include("webapi-velocity")