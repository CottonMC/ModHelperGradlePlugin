import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    compile("com.squareup:javapoet:1.11.1")

    // JUnit Jupiter API and TestEngine implementation
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    testCompile(group = "org.junit.platform", name = "junit-platform-launcher", version = junitPlatformVersion)
    testCompile(group = "org.junit.platform", name = "junit-platform-runner", version = junitPlatformVersion)
    testCompile("io.toolisticon.compiletesting:compiletesting:0.4.0")
    testCompile(group = "com.google.testing.compile", name = "compile-testing", version = "0.15")
    testCompile(
        "org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion"
    )
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
    compile(group= "org.jetbrains.kotlin", name= "kotlin-reflect", version= "1.3.31")
}

publishing {
    publications {
        create<MavenPublication>("mod-helper-processor") {
            artifactId = "mod-helper-processor"
            from(components["java"])
        }
    }
}


tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.suppressWarnings = true
    kotlinOptions.jvmTarget = "1.8"
}