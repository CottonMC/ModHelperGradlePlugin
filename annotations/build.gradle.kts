plugins {
    java
    `maven-publish`
}

group = "io.github.cottonmc"
base {
    archivesBaseName = "mod-helper-annotations"
}
version = "0.0.1"

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "mod-helper-annotations"
            from(components["java"])
        }
    }
}
