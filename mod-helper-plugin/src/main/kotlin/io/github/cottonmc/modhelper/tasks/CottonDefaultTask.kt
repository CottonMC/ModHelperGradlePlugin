package io.github.cottonmc.modhelper.tasks

import io.github.cottonmc.modhelper.extension.ModHelperExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input

abstract class CottonDefaultTask : DefaultTask() {
    init {
        group = "cotton"
    }


    @Input
    var generatedInputPath = "build/classes/java/main/"

    protected fun getModHelperExtension(): ModHelperExtension =
        project.extensions.getByType(ModHelperExtension::class.java)
}
