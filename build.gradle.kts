
plugins {
    id("fabric-loom") version "0.2.1-SNAPSHOT"

    kotlin("jvm") version "1.3.30" apply false
    `java-gradle-plugin`
    //`maven-publish`
}

allprojects {
    apply(plugin="java")

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

    val junitPlatformVersion = "1.5.0-M1"
    val junitJupiterVersion = "5.5.0-M1"

    dependencies {
        // JUnit Jupiter API and TestEngine implementation
        testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

        testCompile(group = "org.junit.platform", name = "junit-platform-launcher", version = junitPlatformVersion)
        testCompile(group = "org.junit.platform", name = "junit-platform-runner", version = junitPlatformVersion)
        testCompile("org.junit-pioneer:junit-pioneer:0.3.0")

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
/*
publishing {
    publications {
        create<MavenPublication>("mod-helper-api") {
            artifactId = "mod-helper-api"
            from(components["java"])
        }
    }
}
*/