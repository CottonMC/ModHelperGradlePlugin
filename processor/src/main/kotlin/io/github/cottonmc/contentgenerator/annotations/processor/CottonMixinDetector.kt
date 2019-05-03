package io.github.cottonmc.contentgenerator.annotations.processor

import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement

class CottonMixinDetector: CottonAnnotationProcessorBase() {
    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {
        return false
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(MIXIN)
    }
}