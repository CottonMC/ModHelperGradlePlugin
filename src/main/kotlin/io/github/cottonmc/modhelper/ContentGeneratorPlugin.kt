package io.github.cottonmc.modhelper

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSetContainer

open class ContentGeneratorPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("java")

        val sourceSetContainer = target.convention.getByType(SourceSetContainer::class.java)




        target.afterEvaluate {

        }

        //target.dependencies.add("compile",":compileGenerated_libsJava")
    }

}