package io.github.cottonmc.modhelper.tasks

import org.gradle.api.tasks.TaskAction

open class EventGeneratorTask:CottonDefaultTask() {


    @TaskAction
    fun taskAction(){
        val extension = getModHelperExtension()
        val processorOutputPath =
            project.file(generatedInputPath)
                .resolve("build")
                .resolve("cotton")
    }
}