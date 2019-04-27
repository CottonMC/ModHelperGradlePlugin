package io.github.cottonmc.modhelper

import io.github.cottonmc.modhelper.extension.ModHelperExtension
import io.github.cottonmc.modhelper.generators.mod.FabricModJson
import io.github.cottonmc.modhelper.generators.mod.FabricModJsonGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateModJsonTask : DefaultTask() {
    @Input
    var generatedResourceRoot: String = "build/cotton"

    val output: File
        get() {
            val resourceRoot = project.file(generatedResourceRoot)
            resourceRoot.mkdirs()
            return resourceRoot.resolve("fabric.mod.json")
        }

    @TaskAction
    fun generateModJson() {
        val extension = project.extensions.getByType(ModHelperExtension::class.java)
        val generator = FabricModJsonGenerator()

        val fabricModJson = FabricModJson().apply {
            id = extension.modid
            name = extension.modname
            version = project.version.toString()
            description = extension.description
        }

        output.writeText(generator.apply(fabricModJson))
    }
}
