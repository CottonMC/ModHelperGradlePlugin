package io.github.cottonmc.contentgenerator.annotations.processor

import io.toolisticon.compiletesting.CompileTestBuilder
import io.toolisticon.compiletesting.GeneratedFileObjectMatcher
import io.toolisticon.compiletesting.JavaFileObjectUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import javax.lang.model.SourceVersion
import javax.tools.StandardLocation

internal class CottonEventsAnnotationProcessorTest {

    @ParameterizedTest
    @ValueSource(
        strings = [
            "SimpeHandler",
            "SimpeHandler2"
        ]
    )
    fun `Compile one class`(source: String) {
        CompileTestBuilder.compilationTest()
            .addSources(JavaFileObjectUtils.readFromResource("/cottonmodhelper/eventhandler/$source.java"))
            .addSources(JavaFileObjectUtils.readFromResource("/cottonmodhelper/eventhandler/Mod.java"))
            .addProcessors(CottonEventsAnnotationProcessor::class.java)
            .compilationShouldSucceed()
            .expectedFileObjectExists(
                StandardLocation.CLASS_OUTPUT,
                "build.cotton.mixin",
                "DummyEventMixin.class", GeneratedFileObjectMatcher { true }
            )
            .testCompilation()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "io.github.cottonmc.modhelper.api.annotations.eventstions.Subscribe"
        ]
    )
    fun `The required annotations are all supported`(type: String) {
        val supportedAnnotationTypes = CottonEventsAnnotationProcessor().supportedAnnotationTypes
        assertTrue(
            supportedAnnotationTypes.contains(type),
            "required annotation '$type' is not supported! supported: $supportedAnnotationTypes"
        )
    }

    @Test
    fun `Only java 8 is supported`() {
        assertEquals(
            SourceVersion.RELEASE_8,
            CottonEventsAnnotationProcessor().supportedSourceVersion,
            "only java 8 should be supported!"
        )
    }
}