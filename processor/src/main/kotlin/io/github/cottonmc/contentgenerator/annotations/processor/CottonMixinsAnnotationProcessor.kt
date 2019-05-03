package io.github.cottonmc.contentgenerator.annotations.processor

import io.github.cottonmc.modhelper.api.side.Side
import io.github.cottonmc.modhelper.api.side.Sided
import java.util.Locale
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation

internal class CottonMixinsAnnotationProcessor : AbstractProcessor() {
    private var processed = false

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        if (processed) return false
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "[Cotton] Processing mixins...")

        val mixins = HashMap<String, MutableList<String>>()

        fun addMixin(side: String, mixin: String) =
            mixins.getOrPut(side) { ArrayList() }.add(mixin)

        val mixinAnnotation = processingEnv.elementUtils.getTypeElement(MIXIN_ANNOTATION)

        loop@ for (element in roundEnv.getElementsAnnotatedWith(mixinAnnotation)) {
            val reference: String = getBinaryName(element as TypeElement)
            val sidedAnnotation = element.getAnnotation(Sided::class.java)
            val side = sidedAnnotation?.value ?: Side.COMMON

            addMixin(
                side.name.toLowerCase(Locale.ENGLISH),
                reference
            )
        }

        for (side in mixins.keys) {
            val initializerOutput = processingEnv.filer.createResource(
                StandardLocation.SOURCE_OUTPUT,
                "", "build/cotton/$side.json"
            )


            initializerOutput.openWriter().use {
                for (mixin in mixins[side]!!) {
                    it.write("$mixin\n")
                }
            }
        }

        processed = true

        return false
    }

    override fun getSupportedAnnotationTypes() = setOf(MIXIN_ANNOTATION)

    //only support release 8, we do not want to mess with mixins.
    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_8

    private fun getBinaryName(element: TypeElement): String =
        processingEnv.elementUtils.getBinaryName(element).toString()

    companion object {
        private const val MIXIN_ANNOTATION = "org.spongepowered.asm.mixin.Mixin"
    }
}
