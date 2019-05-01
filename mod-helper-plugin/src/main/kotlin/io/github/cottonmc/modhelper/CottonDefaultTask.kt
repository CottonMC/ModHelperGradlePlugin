package io.github.cottonmc.modhelper

import org.gradle.api.DefaultTask

abstract class CottonDefaultTask : DefaultTask() {
    init {
        group = "cotton"
    }
}
