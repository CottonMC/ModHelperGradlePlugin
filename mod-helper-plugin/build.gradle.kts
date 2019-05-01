import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
     //id("fabric-loom") 

    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
}

group = "io.github.cottonmc"
version = "0.0.1"


val junitPlatformVersion = "1.5.0-M1"
val junitJupiterVersion = "5.5.0-M1"

dependencies {
    testCompile(gradleTestKit())
    implementation(kotlin("stdlib-jdk8"))
    compile("com.squareup:javapoet:1.11.1")
    compile(group = "io.github.cottonmc", name = "json-factory", version = "0.4.1")
    compile(group = "com.google.code.gson", name = "gson", version = "2.8.5")
    // JUnit Jupiter API and TestEngine implementation
    testCompile("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    testCompile(group = "org.junit.platform", name = "junit-platform-launcher", version = junitPlatformVersion)
    testCompile(group = "org.junit.platform", name = "junit-platform-runner", version = junitPlatformVersion)
    testCompile(group = "com.google.testing.compile", name = "compile-testing", version = "0.15")
    compile(project(":annotations"))
    testCompile("org.junit-pioneer:junit-pioneer:0.3.0")
    //implementation(group="com.google.auto.service",name="auto-service",version="1.0")
    //annotationProcessor(group="com.google.auto.service",name="auto-service",version="1.0")
}

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        this.setUrl("http://server.bbkr.space:8081/artifactory/libs-release")
        name = "cotton"
    }
}

gradlePlugin {
    plugins {
        create("cotton-mod-helper") {
            id = "io.github.cottonmc.cotton-mod-helper"
            implementationClass = "io.github.cottonmc.modhelper.ContentGeneratorPlugin"
        }
    }
}
tasks.named<Test>("test") {
    testLogging.showStackTraces = true
    useJUnitPlatform()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
