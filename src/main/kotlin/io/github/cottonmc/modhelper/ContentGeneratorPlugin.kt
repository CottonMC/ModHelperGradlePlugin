package io.github.cottonmc.modhelper

import io.github.cottonmc.modhelper.extension.ModHelperExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

open class ContentGeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("java")

        val sourceSetContainer = target.convention.getByType(SourceSetContainer::class.java)

        target.repositories.maven {
            it.setUrl("http://server.bbkr.space:8081/artifactory/libs-snapshot")
            it.name = "Cotton Snapshot"
        }
        target.repositories.mavenLocal()
        target.extensions.create("modHelper", ModHelperExtension::class.java)

        target.afterEvaluate {
            val extension = target.extensions.getByType(ModHelperExtension::class.java)
            // TODO: Fix artifact IDs
            target.dependencies.add("compileOnly", "io.github.cottonmc:annotations:0.1.1-SNAPSHOT")
            target.dependencies.add(extension.annotationProcessor.configuration, "io.github.cottonmc:processor:0.1.1-SNAPSHOT")

            target.tasks.create("generateModJson", GenerateModJsonTask::class.java)
            target.tasks.getByPath("jar").dependsOn("generateModJson")
        }

        //target.dependencies.add("compile",":compileGenerated_libsJava")
    }

}