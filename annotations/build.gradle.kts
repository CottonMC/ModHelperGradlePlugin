plugins {
    java
    `maven-publish`
}

group = "io.github.cottonmc"
base {
    archivesBaseName = "mod-helper-annotations"
}
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
