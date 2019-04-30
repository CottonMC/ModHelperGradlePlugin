package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.gson.Gson
import io.github.cottonmc.modhelper.annotations.Subscribe
import io.github.cottonmc.modhelper.api.events.EventDescriptor
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic
import javax.tools.StandardLocation

class CottonEventsAnnotationProcessor : AbstractProcessor() {
    private var processed = false

    override fun process(p0: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Starting to process event handlers...")


        val eventHandlers = HashMap<String, MutableList<String>>()

        fun addHandler(eventType: String, handlerType: String) =
            eventHandlers.getOrPut(eventType) { ArrayList() }.add(handlerType)
        if (processed) return false
        loop@ for (element in roundEnv.getElementsAnnotatedWith(Subscribe::class.java)) {
            if (element !is TypeElement) {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "annotation is on non type element: ${element.simpleName}"
                )
                continue@loop
            }

            val typeMirror = element.interfaces.firstOrNull { mirror ->

                if (mirror is DeclaredType) {
                    val annotations = mirror.asElement().getAnnotationsByType(EventDescriptor::class.java)
                    if (annotations.isEmpty()) {
                        processingEnv.messager.printMessage(
                            Diagnostic.Kind.WARNING,
                            "non event handler supertype found on: ${element.simpleName}"
                        )
                        return@firstOrNull false
                    }
                    return@firstOrNull true
                }
                false
            } ?: continue@loop

            val eventDescriptor = typeMirror.getAnnotation(EventDescriptor::class.java)
            addHandler(
                eventType = (typeMirror as DeclaredType).toString(),
                handlerType = getBinaryName(element)
            )
        }

        val initializerOutput = processingEnv.filer.createResource(
            StandardLocation.SOURCE_OUTPUT,
            "", "build/cotton/eventhandlers.json"
        )

        val gson = Gson()

        initializerOutput.openWriter().use {
            it.write(gson.toJson(eventHandlers))
        }

        processed = true

        return processed
    }

    private fun getBinaryName(element: TypeElement): String =
        processingEnv.elementUtils.getBinaryName(element).toString()

    override fun getSupportedAnnotationTypes() = setOf(
        Subscribe::class.java.name
    )

    //only support release 8, we do not want to mess with mixins.
    override fun getSupportedSourceVersion() = SourceVersion.RELEASE_8
}