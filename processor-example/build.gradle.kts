plugins {
    java
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    mavenCentral()
}

// TODO: Remove
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly(project(":annotations"))
    annotationProcessor(project(":processor"))
    kapt(project(":processor"))
}
