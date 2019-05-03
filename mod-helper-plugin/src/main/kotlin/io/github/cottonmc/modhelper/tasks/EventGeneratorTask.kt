package io.github.cottonmc.modhelper.tasks

import io.github.cottonmc.modhelper.generators.eventgenerator.MixinGenerator
import org.gradle.api.tasks.TaskAction

open class EventGeneratorTask : CottonDefaultTask() {


    @TaskAction
    fun taskAction() {
        val extension = getModHelperExtension()
        val processorOutputPath =
            project.file(generatedInputPath)
                .resolve("build")
                .resolve("cotton")

        if (extension.enableMixinProcessing) {
            MixinGenerator(
                extension.defaultpackage,
                extension.generatedResourceOutputPath,
                extension.modid,
                ""
            ).generate()
        }
    }
}