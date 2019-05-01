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


        val eventHandlers = HashMap<String, Any>()

        fun addHandler(eventType: String, handler: String,data:Map<String,Any>): Any {
            val storedEvent = eventHandlers.getOrPut(eventType) { data }
            val events = (storedEvent as MutableMap<String, Any>).getOrPut("handlers", { ArrayList<String>() })
            (events as MutableList<String>).add(handler)

            return storedEvent
        }

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

            val eventDescriptor = (typeMirror as DeclaredType).asElement().getAnnotation(EventDescriptor::class.java)
            addHandler(
                eventType = (typeMirror as DeclaredType).toString(),
                handler = getBinaryName(element),
                data = mapOf(
                    "mixinString" to eventDescriptor.mixinString,
                    "targetClass" to eventDescriptor.targetClass,
                    "type" to eventDescriptor.type,
                    "cancellable" to eventDescriptor.cancelleable
                )
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