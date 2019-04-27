plugins {
    id("fabric-loom") version "0.2.1-SNAPSHOT"
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "4.0.2"
    java
}

group = "io.github.cottonmc"
version = "0.1.0"

repositories {
    maven {
        setUrl("http://maven.fabricmc.net/")
        name = "Fabric"
    }
    maven {
        setUrl("https://kotlin.bintray.com/kotlinx")
        name = "Kotlin X"
    }
    maven {
        setUrl("http://server.bbkr.space:8081/artifactory/libs-release")
        name = "cotton"
    }
    maven {
        setUrl("http://server.bbkr.space:8081/artifactory/libs-snapshot")
        name = "cotton"
    }
    mavenCentral()
    mavenLocal()
}
val minecraft_version :String by project
val yarn_mappings :String by project
val fabric_version:String by project

dependencies {

    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings")

    modCompile("net.fabricmc:fabric:$fabric_version")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}