import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
     //id("fabric-loom") 

    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
}
val mod_version: String by project

group = "io.github.cottonmc"
version = mod_version



dependencies {
    testCompile(gradleTestKit())
    implementation(kotlin("stdlib-jdk8"))
    compile("com.squareup:javapoet:1.11.1")
    compile(group = "io.github.cottonmc", name = "json-factory", version = "0.4.1")
    compile(group = "com.google.code.gson", name = "gson", version = "2.8.5")
    compile(project(":annotations"))
    // https://mvnrepository.com/artifact/commons-io/commons-io
    testCompile(group= "commons-io", name= "commons-io", version= "2.6")

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
