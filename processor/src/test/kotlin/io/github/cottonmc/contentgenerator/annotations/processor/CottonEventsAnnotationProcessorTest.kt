package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.toolisticon.compiletesting.CompileTestBuilder
import io.toolisticon.compiletesting.GeneratedFileObjectMatcher
import io.toolisticon.compiletesting.JavaFileObjectUtils
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
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
            .addProcessors(CottonEventsAnnotationProcessor::class.java)
            .compilationShouldSucceed()
            .expectedFileObjectExists(
                StandardLocation.SOURCE_OUTPUT,
                "build.cotton",
                "eventhandlers.json",
                GeneratedFileObjectMatcher(({
                    if (it.name == "/SOURCE_OUTPUT/build/cotton/eventhandlers.json") {
                        val reader = it.openReader(true)
                        val readText = reader.readText()
                        val generated = Gson().fromJson<JsonObject>(readText, JsonObject::class.java)

                        val content = "/cottonmodhelper/eventhandler/results/$source.json".loadLines()
                            .joinToString(separator = "")

                        val expected = Gson().fromJson<JsonObject>(content, JsonObject::class.java)

                        assertEquals(expected, generated, "generated file contents are invalid")
                    }

                    true
                }))
            )
            .testCompilation()
    }

    @Test
    fun `Compile 2 classes`() {
        CompileTestBuilder.compilationTest()
            .addSources(JavaFileObjectUtils.readFromResource("/cottonmodhelper/eventhandler/SimpeHandler.java"))
            .addSources(JavaFileObjectUtils.readFromResource("/cottonmodhelper/eventhandler/SimpeHandler2.java"))
            .addProcessors(CottonEventsAnnotationProcessor::class.java)
            .compilationShouldSucceed()
            .expectedFileObjectExists(
                StandardLocation.SOURCE_OUTPUT,
                "build.cotton",
                "eventhandlers.json",
                GeneratedFileObjectMatcher(({
                    if (it.name == "/SOURCE_OUTPUT/build/cotton/eventhandlers.json") {
                        val reader = it.openReader(true)
                        val readText = reader.readText()
                        val generated = Gson().fromJson<JsonObject>(readText, JsonObject::class.java)

                        val content = "/cottonmodhelper/eventhandler/results/SimpeHandlerDouble.json"
                            .loadLines()
                            .joinToString(separator = "")

                        val expected = Gson().fromJson<JsonObject>(content, JsonObject::class.java)

                        assertEquals(expected, generated, "generated file contents are invalid")
                    }

                    true
                }))
            )
            .testCompilation()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "io.github.cottonmc.modhelper.annotations.Subscribe"
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