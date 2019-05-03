package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.toolisticon.compiletesting.CompileTestBuilder.compilationTest
import io.toolisticon.compiletesting.GeneratedFileObjectMatcher
import io.toolisticon.compiletesting.JavaFileObjectUtils
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import javax.lang.model.SourceVersion
import javax.tools.StandardLocation

internal class CottonInitializerAnnotationProcessorTest {


    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "EntrypointDetection",
            "MixedAdapters",
            "MultipleInitializers",
            "SingleInitializer"
        ]
    )
    fun `the processor recognises classes with initializers`(source: String) {

        val executables = ArrayList<Executable>()
        compilationTest()
            .addSources(JavaFileObjectUtils.readFromResource("/cottonmodhelper/moddetector/$source.java"))
            .addProcessors(CottonInitializerAnnotationProcessor::class.java)
            .compilationShouldSucceed()
            .expectedFileObjectExists(
                StandardLocation.SOURCE_OUTPUT,
                "build.cotton",
                "initializers.json",
                GeneratedFileObjectMatcher(({
                    println(it.name)
                    if(it.name == "/SOURCE_OUTPUT/build/cotton/initializers.json")
                    {
                        val reader = it.openReader(true)
                        val readText = reader.readText()
                        val generated = Gson().fromJson<JsonObject>(readText, JsonObject::class.java)

                        val content = "/cottonmodhelper/moddetector/results/$source.json".loadLines()
                            .joinToString(separator = "")

                        val expected = Gson().fromJson<JsonObject>(content, JsonObject::class.java)

                        executables.add(Executable{ assertEquals(expected,generated, "generated file contents are invalid")})
                    }

                    true
                }))
                //JavaFileObjectUtils.readFromString("")
            )
            .testCompilation()

        assertAll(executables)

    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "io.github.cottonmc.modhelper.annotations.Initializer"
        ]
    )
    fun `The required annotations are all supported`(type: String) {
        val supportedAnnotationTypes = CottonInitializerAnnotationProcessor().supportedAnnotationTypes
        assertTrue(supportedAnnotationTypes.contains(type), "required annotation '$type' is not supported!")
    }

    @Test
    fun `Only java 8 is supported`() {
        assertEquals(
            SourceVersion.RELEASE_8,
            CottonInitializerAnnotationProcessor().supportedSourceVersion,
            "only java 8 should be supported!"
        )
    }


}