package io.github.cottonmc.modhelper

import io.github.cottonmc.modhelper.extension.ModHelperExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.tasks.Jar

open class ContentGeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("java")

        val sourceSetContainer = target.convention.getByType(SourceSetContainer::class.java)

        target.repositories.maven {
            it.setUrl("http://server.bbkr.space:8081/artifactory/libs-snapshot")
            it.name = "Cotton Snapshot"
        }
        target.repositories.mavenLocal()
        val extension = target.extensions.create("modHelper", ModHelperExtension::class.java)
        val generateModJson = target.tasks.create("generateModJson", GenerateModJsonTask::class.java)

        extension.modid = target.name.replace(" ","_").toLowerCase()
        extension.modname = target.name

        target.afterEvaluate {
            target.dependencies.add("compileOnly", "io.github.cottonmc:mod-helper-annotations:0.0.1")
            target.dependencies.add(extension.annotationProcessor.configuration, "io.github.cottonmc:mod-helper-processor:0.0.1")

            (target.tasks.getByName("jar") as Jar).from(generateModJson.output)
            target.tasks.getByPath("jar").dependsOn("generateModJson")
        }
    }

}
