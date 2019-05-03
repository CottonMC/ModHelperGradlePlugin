package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.gson.Gson
import io.github.cottonmc.modhelper.api.initializer.Initializer
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic
import javax.tools.StandardLocation

internal class CottonInitializerAnnotationProcessor : CottonAnnotationProcessorBase() {
    private var processed = false

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        if (processed) return false
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "[Cotton] Processing initializers...")

        val initializers = HashMap<String, MutableList<Any>>()

        fun addInitializer(entrypointType: String, initializer: Any) =
            initializers.getOrPut(entrypointType) { ArrayList() }.add(initializer)

        loop@ for (element in roundEnv.getElementsAnnotatedWith(Initializer::class.java)) {
            val reference: String = when (element) {
                is TypeElement -> getBinaryName(element)
                is ExecutableElement -> getBinaryName(element.enclosingElement as TypeElement) + "::" +
                        element.simpleName
                is VariableElement -> getBinaryName(element.enclosingElement as TypeElement) + "::" +
                        element.simpleName

                else -> {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Unknown element type: ${element.kind}")
                    continue@loop
                }
            }

            val annotation = element.getAnnotation(Initializer::class.java)
            var entrypointType = annotation.value

            if (entrypointType.isEmpty()) {
                if (element !is TypeElement) {
                    processingEnv.messager.printMessage(Diagnostic.Kind.ERROR, "Missing entrypointType on a non-type element: ${element.simpleName}")
                    continue@loop
                }

                for (type in AutomaticEntrypointTypes.values()) {
                    val hasMarker = element.interfaces.any {
                        it is DeclaredType &&
                                (it.asElement() as? TypeElement)?.qualifiedName.toString() == type.markerInterface
                    }

                    if (hasMarker) {
                        entrypointType = type.entrypointType
                        break
                    }
                }
            }

            addInitializer(
                entrypointType,
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

    private enum class AutomaticEntrypointTypes(val markerInterface: String, val entrypointType: String) {
        MAIN(MOD_INITIALIZER, "main"),
        CLIENT(CLIENT_MOD_INITIALIZER, "client"),
        SERVER(SERVER_MOD_INITIALIZER, "server")
    }
}
