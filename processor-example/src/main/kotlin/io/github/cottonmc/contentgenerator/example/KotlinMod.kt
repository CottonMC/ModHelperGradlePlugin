package io.github.cottonmc.contentgenerator.example

import io.github.cottonmc.contentgenerator.annotations.Initializer

object KotlinMod {
    @Initializer(adapter = "kotlin")
    fun init() {

    }

    @Initializer(entrypointType = "client", adapter = "kotlin")
    fun initClient() {

    }
}
