package io.github.cottonmc.modhelper

import io.github.cottonmc.modhelper.extension.ModHelperExtension
import io.github.cottonmc.modhelper.generators.mod.FabricEntrypointContainer
import io.github.cottonmc.modhelper.generators.mod.FabricModJson
import io.github.cottonmc.modhelper.generators.mod.FabricModJsonGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class GenerateModJsonTask : CottonDefaultTask() {
    val output: File
        get() {
            val resourceRoot = project.file(getModHelperExtension().generatedResourceOutputPath)
            resourceRoot.mkdirs()
            return resourceRoot.resolve("fabric.mod.json")
        }

    @Input
    var generatedInputPath = "build/classes/java/main/"

    @TaskAction
    fun generateModJson() {
        val extension = getModHelperExtension()
        val generator = FabricModJsonGenerator()
        val processorOutputPath =
            project.file(generatedInputPath)
                .resolve("build")
                .resolve("cotton")

        val fabricModJson = FabricModJson().apply {
            id = extension.modid
            name = extension.modName
            version = extension.version
            description = extension.description
            entrypoints = generator.gson.fromJson(
                processorOutputPath.resolve("initializers.json").reader(),
                FabricEntrypointContainer::class.java
            )
        }

        output.writeText(generator.apply(fabricModJson))
    }

    private fun getModHelperExtension(): ModHelperExtension =
        project.extensions.getByType(ModHelperExtension::class.java)
}
