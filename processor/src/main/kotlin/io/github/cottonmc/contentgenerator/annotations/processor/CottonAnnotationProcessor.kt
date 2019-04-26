package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.gson.Gson
import io.github.cottonmc.contentgenerator.annotations.Initializer
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation

internal class CottonAnnotationProcessor : AbstractProcessor() {
    private var processed = false

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Processing...")
        if (processed) return false

        val initializers = HashMap<String, MutableList<Any>>()

        fun addInitializer(entrypointType: String, initializer: Any) =
            initializers.getOrPut(entrypointType) { ArrayList() }.add(initializer)

        loop@ for (element in roundEnv.getElementsAnnotatedWith(Initializer::class.java)) {
            val reference: String = when (element) {
                is TypeElement -> element.qualifiedName.toString()
                is ExecutableElement -> (element.enclosingElement as TypeElement).qualifiedName.toString() + "::" +
                        element.simpleName
                is VariableElement -> (element.enclosingElement as TypeElement).qualifiedName.toString() + "::" +
                        element.simpleName

                else -> {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Unknown element type: ${element.kind}")
                    continue@loop
                }
            }

            val annotation = element.getAnnotation(Initializer::class.java)
            addInitializer(
                annotation.entrypointType,
                if (annotation.adapter == "") {
                    reference
                } else mapOf("value" to reference, "adapter" to annotation.adapter)
            )
        }

        val initializerOutput = processingEnv.filer.createResource(
            StandardLocation.SOURCE_OUTPUT,
            "", "build/cotton/initializers.json"
        )

        val gson = Gson()

        initializerOutput.openWriter().use {
            it.write(gson.toJson(initializers))
        }

        processed = true

        return false
    }

    override fun getSupportedAnnotationTypes() = setOf(
        Initializer::class.java.name
    )

    override fun getSupportedSourceVersion() = SourceVersion.latestSupported()
}
