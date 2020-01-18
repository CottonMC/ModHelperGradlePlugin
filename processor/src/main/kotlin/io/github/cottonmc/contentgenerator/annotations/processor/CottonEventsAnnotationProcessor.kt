package io.github.cottonmc.contentgenerator.annotations.processor

import io.github.cottonmc.modhelper.api.annotations.events.EventDescriptor
import io.github.cottonmc.modhelper.api.annotations.events.Subscribe
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.tools.Diagnostic
import java.util.*
import javax.lang.model.element.Element
import kotlin.collections.HashMap


class CottonEventsAnnotationProcessor : CottonAnnotationProcessorBase() {


    override fun doProcess(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment) {
        processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, "Starting to process event handlers...")

        val handlers = HashMap<DeclaredType, ArrayList<Element>>()

        loop@ for (element in roundEnv.getElementsAnnotatedWith(io.github.cottonmc.modhelper.api.annotations.Subscribe::class.java)) {
            if (element !is TypeElement) {
                processingEnv.messager.printMessage(
                    Diagnostic.Kind.ERROR,
                    "annotation is on non type element: ${element.simpleName}"
                )
                continue@loop
            }

            val typeMirror = element.interfaces.firstOrNull { mirror ->

                if (mirror is DeclaredType) {
                    val annotations = mirror.asElement()
                        .getAnnotationsByType(io.github.cottonmc.modhelper.api.annotations.EventDescriptor::class.java)
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

            typeMirror
            val eventDescriptor = typeMirror.asElement()
                .getAnnotation(io.github.cottonmc.modhelper.api.annotations.EventDescriptor::class.java)
            handlers.getOrDefault(typeMirror, ArrayList()).add(element)
        }


    }

    override fun getOwnedAnnotations(): MutableSet<String> = mutableSetOf(
        io.github.cottonmc.modhelper.api.annotations.Subscribe::class.java.canonicalName
    )

    //mixin annotation
    internal class templateMixin {

        //not implemented here
        interface handler {
            fun handle(arg: String)
        }

        val services = ServiceLoader.load(handler::class.java)

        //inject annotation
        fun run(arg: String) {
            for (service in services) {
                service.handle(arg)
            }
        }
    }
}