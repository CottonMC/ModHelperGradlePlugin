plugins {
    java
    kotlin("jvm")
    `maven-publish`
}

repositories {
    mavenCentral()
}

group = "io.github.cottonmc"
base {
    archivesBaseName = "mod-helper-processor"
}
version = "0.0.1"

dependencies {
    api(kotlin("stdlib-jdk8"))
    api(project(":annotations"))
    implementation("com.google.code.gson:gson:2.8.5")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "mod-helper-processor"
            from(components["java"])
        }
    }
}
