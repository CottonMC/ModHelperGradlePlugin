package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.testing.compile.CompilationSubject.assertThat
import com.google.testing.compile.Compiler.javac
import com.google.testing.compile.JavaFileObjects
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion

internal class CottonAnnotationProcessorTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    //TODO add class sources to this
    @ParameterizedTest
    @ValueSource(
        strings = [
            """
                class Init implements ClientInitializer{


                }
            """
        ]
    )
    fun `the processor recognises clases with initializers`(source:String) {
        val compilation =
            javac()
                .compile(
                    JavaFileObjects
                        .forSourceString(
                            "HelloWorld",
                            "final class HelloWorld {}"
                        )
                )
        assertThat(compilation).succeeded()
    }

    @ParameterizedTest
    @ValueSource(
        strings = [
            "io.github.cottonmc.modhelper.annotations.Initializer"
        ]
    )
    fun `The required annotations are all supported`(type: String) {
        val supportedAnnotationTypes = CottonAnnotationProcessor().supportedAnnotationTypes
        assertTrue(supportedAnnotationTypes.contains(type), "required annotation '$type' is not supported!")
    }

    @Test
    fun `Only java 8 is supported`() {
        assertEquals(
            SourceVersion.RELEASE_8,
            CottonAnnotationProcessor().supportedSourceVersion,
            "only java 8 should be supported!"
        )
    }
}