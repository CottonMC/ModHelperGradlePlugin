package io.github.cottonmc.contentgenerator.annotations.processor

import io.github.cottonmc.contentgenerator.data.FabricModJsonManager
import io.github.cottonmc.modhelper.api.annotations.events.Subscribe
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

internal class CottonMixinsAnnotationProcessor : CottonAnnotationProcessorBase() {
    override fun getOwnedAnnotations() =
        mutableSetOf(io.github.cottonmc.modhelper.api.annotations.Subscribe::class.java.name)

    override fun doProcess(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "[Cotton] Processing mixins...")

        val mixins = HashMap<Side, MutableList<String>>()

        fun addMixin(side: Side, mixin: String) =
            mixins.getOrPut(side) { ArrayList() }.add(mixin)

        for (element in roundEnv.getElementsAnnotatedWith(io.github.cottonmc.modhelper.api.annotations.Subscribe::class.java)) {
            if (element.annotationMirrors.none { getBinaryName(it.annotationType.asElement() as TypeElement) == MIXIN }) {
                continue
            }

            val reference: String = getBinaryName(element as TypeElement)
            val sidedAnnotation = element.getAnnotation(Sided::class.java)
            val side = sidedAnnotation.value ?: Side.COMMON

            addMixin(
                side,
                reference
            )
        }

        mixins.forEach {
            FabricModJsonManager.createMixinFor(processingEnv, it.key, it.value.toTypedArray())
        }
    }
}
