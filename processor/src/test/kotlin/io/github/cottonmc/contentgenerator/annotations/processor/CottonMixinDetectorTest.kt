package io.github.cottonmc.contentgenerator.annotations.processor

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import javax.lang.model.SourceVersion

internal class CottonMixinDetectorTest {

    @Test
    fun process() {
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "org.spongepowered.asm.mixin.Mixin"
        ]
    )
    fun `The required annotations are all supported`(type: String) {
        val supportedAnnotationTypes = CottonMixinsAnnotationProcessor().supportedAnnotationTypes
        assertTrue(supportedAnnotationTypes.contains(type), "required annotation '$type' is not supported!")
    }

    @Test
    fun `Only java 8 is supported`() {
        assertEquals(
            SourceVersion.RELEASE_8,
            CottonMixinsAnnotationProcessor().supportedSourceVersion,
            "only java 8 should be supported!"
        )
    }
}