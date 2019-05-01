
plugins {
    id("fabric-loom") version "0.2.1-SNAPSHOT"

    kotlin("jvm") version "1.3.30" apply false
    `java-gradle-plugin`
    `maven-publish`
}

allprojects{

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
}

val minecraft_version: String by project
val yarn_mappings: String by project
val fabric_version: String by project
val loader_version: String by project

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings("net.fabricmc:yarn:$yarn_mappings")
    modCompile("net.fabricmc:fabric-loader:${loader_version}")

    compile(project(":annotations"))
   // modCompile("net.fabricmc:fabric:$fabric_version")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "mod-helper-api"
            from(components["java"])
        }
    }
}
