plugins {
    id("com.gradleup.shadow")
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation(project(":webapi"))
    compileOnly("com.velocitypowered:velocity-api:3.5.0-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.5.0-SNAPSHOT")
}

tasks.shadowJar {
    configurations = listOf(project.configurations.runtimeClasspath.get())
    archiveFileName.set("WebApi-Velocity.jar")
}