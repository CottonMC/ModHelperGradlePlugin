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

val junitPlatformVersion = "1.5.0-M1"
val junitJupiterVersion = "5.5.0-M1"

dependencies {
    api(kotlin("stdlib-jdk8"))
    api(project(":annotations"))
    implementation("com.google.code.gson:gson:2.8.5")

    // JUnit Jupiter API and TestEngine implementation
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    testCompile(group = "org.junit.platform", name = "junit-platform-launcher", version = junitPlatformVersion)
    testCompile(group = "org.junit.platform", name = "junit-platform-runner", version = junitPlatformVersion)
    testCompile(group = "com.google.testing.compile", name = "compile-testing", version = "0.15")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
