package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.gson.Gson
import io.github.cottonmc.modhelper.api.events.EventDescriptor
import io.github.cottonmc.modhelper.api.events.Subscribe
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic
import javax.tools.StandardLocation

class CottonEventsAnnotationProcessor : CottonAnnotationProcessorBase() {

    override fun doProcess(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Starting to process event handlers...")


        val eventHandlers = HashMap<String, Any>()

        fun addHandler(eventType: String, handler: String, data: Map<String, Any>): Any {
            val storedEvent = eventHandlers.getOrPut(eventType) { data }
            val events = (storedEvent as MutableMap<String, Any>).getOrPut("handlers", { ArrayList<String>() })
            (events as MutableList<String>).add(handler)

            return storedEvent
        }

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
            typeMirror as DeclaredType

            val eventDescriptor = typeMirror.asElement().getAnnotation(EventDescriptor::class.java)


            val method= typeMirror.asElement().enclosedElements[0]

            addHandler(
                eventType = typeMirror.toString(),
                handler = getBinaryName(element),
                data = mapOf(
                    "mixinString" to eventDescriptor.mixinString,
                    "targetClass" to eventDescriptor.targetClass,
                    "type" to eventDescriptor.type,
                    "returnValue" to eventDescriptor.returnType,
                    "side" to eventDescriptor.side.toString(),
                    "handlerMethod" to method.toString()
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

    }

    override fun getSupportedAnnotationTypes() = setOf(
        Subscribe::class.java.name
    )
}