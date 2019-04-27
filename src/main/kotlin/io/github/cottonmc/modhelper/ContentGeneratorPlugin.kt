package io.github.cottonmc.modhelper

import io.github.cottonmc.modhelper.extension.ModHelperExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar
import org.gradle.language.jvm.tasks.ProcessResources

open class ContentGeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("java")

        val sourceSetContainer = target.convention.getByType(SourceSetContainer::class.java)

        // Add Cotton maven for mod-helper dependencies
        target.repositories.maven {
            it.setUrl("http://server.bbkr.space:8081/artifactory/libs-snapshot")
            it.name = "Cotton Snapshot"
        }

        // TODO: Remove
        target.repositories.mavenLocal()

        val extension = target.extensions.create("modHelper", ModHelperExtension::class.java)
        val generateModJson = target.tasks.create("generateModJson", GenerateModJsonTask::class.java)

        // Set up defaults for fabric.mod.json
        extension.modid = target.name.replace(" ","_").toLowerCase()
        extension.modName = target.name
        extension.version = target.version.toString()

        target.afterEvaluate {
            // Add basic mod-helper dependencies
            target.dependencies.add("compileOnly", "io.github.cottonmc:mod-helper-annotations:0.0.1")
            target.dependencies.add(extension.annotationProcessor.configuration, "io.github.cottonmc:mod-helper-processor:0.0.1")

            // TODO: Figure out why runClient doesn't find a mod
            // Add fabric.mod.json
            val processResources = (target.tasks.getByName("processResources") as ProcessResources)
            processResources.from(generateModJson.output)
            processResources.dependsOn(generateModJson)

            // Make sure that the annotation processor gets run before generateModJson
            generateModJson.dependsOn(target.tasks.getByName("compileJava"))

            // Remove build/cotton files from the output
            if (!extension.debug) {
                (target.tasks.getByName("jar") as Jar).exclude(extension.cottonGeneratedOutputPath)
            }
        }
    }

}
