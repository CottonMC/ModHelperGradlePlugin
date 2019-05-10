package io.github.cottonmc.contentgenerator.annotations.processor

import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement

abstract class CottonAnnotationProcessorBase: AbstractProcessor() {
    //only support release 8, we do not want to mess with mixins.
    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_8

    private var processed = false

    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment): Boolean {

        if (!processed)
            doProcess(p0, p1)

        processed = true

        return false
    }

    abstract fun doProcess(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment)


    protected fun getBinaryName(element: TypeElement): String =
        processingEnv.elementUtils.getBinaryName(element).toString()

    companion object {
        const val MOD_INITIALIZER = "net.fabricmc.api.ModInitializer"
        const val CLIENT_MOD_INITIALIZER = "net.fabricmc.api.ClientModInitializer"
        const val SERVER_MOD_INITIALIZER = "net.fabricmc.api.DedicatedServerModInitializer"
        const val MIXIN = "org.spongepowered.asm.mixin.Mixin"
        const val ENVIRONMENT = "net.fabricmc.api.Environment"
    }
}