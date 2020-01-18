package io.github.cottonmc.contentgenerator.eventgenerator

import com.squareup.javapoet.*
import com.squareup.javapoet.TypeSpec.classBuilder
import io.github.cottonmc.modhelper.api.annotations.events.EventDescriptor


class MixinGenerator(
    packageName: String,
    private val modname: String,
    private val generatedSourceFolder: Appendable?
) {

    private val packageName = "$packageName.mixin"
    private val mixinAnnotation = ClassName.get("org.spongepowered.asm.mixin", "Mixin")
    private val injectAnnotation = ClassName.get("org.spongepowered.asm.mixin.injection", "Inject")
    private val atAnnotation = ClassName.get("import org.spongepowered.asm.mixin.injection", "At")
    private val callBackInfo = ClassName.get("org.spongepowered.asm.mixin.injection.callback", "CallbackInfoReturnable")
    private val eventControl = ClassName.get("io.github.cottonmc.modhelper.api.annotations.events", "EventControl")

    fun generate(
        eventName: String,
        mixin: String,
        target: String,
        type: io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType,
        handlers: Array<String>,
        method: String,
        returnName: String
    ) {

        val eventNameParts = eventName.split(".")
        val identifier = eventNameParts.last()

        val eventPackage = eventNameParts.subList(0, eventNameParts.size - 1).joinToString(separator = ".")
        val eventInterface = ClassName.get(eventPackage, eventName)

        val returnType =
            when (returnName) {
                "" -> ClassName.BOOLEAN.box()
                else -> {
                    val returnNameParts = returnName.split(".")
                    val returnPackage =
                        returnNameParts.dropLast(1).joinToString(separator = ".")
                    ClassName.get(returnPackage, returnNameParts.last())
                }
            }

        val cir = ParameterizedTypeName.get(callBackInfo, returnType)


        val modNameCapital = modname.mapIndexed { i, c ->
            if (i == 0)
                return@mapIndexed c.toUpperCase()
            c
        }.joinToString(separator = "")

        val methodData = method.replace(")", "").split("(")

        var injector = AnnotationSpec.builder(injectAnnotation)
            .addMember("method", CodeBlock.of("\"$mixin\""))

        injector = when (type) {
            io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.BEFORE -> {
                injector
                    .addMember(
                        "cancellable",
                        CodeBlock.of("false")
                    )
                    .addMember(
                        "at",
                        CodeBlock.of("@\$T(\"HEAD\")", atAnnotation)
                    )
            }
            io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.AFTER -> {
                injector.addMember(
                    "cancellable",
                    CodeBlock.of("false")
                )
            }
            io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.BEFORE_CANCELLABLE -> {
                injector
                    .addMember(
                        "cancellable",
                        CodeBlock.of("true")
                    )
                    .addMember(
                        "at",
                        CodeBlock.of("@\$T(\"HEAD\")", atAnnotation)
                    )
            }
        }

        val handlerMethod = MethodSpec
            .methodBuilder(methodData[0])
            .addAnnotation(
                injector.build()
            )

        var parameterCount = 0
        methodData[1].split(",").forEachIndexed { index, s ->
            val split1 = s.split(".")

            val className = ClassName.get(
                split1.dropLast(1).joinToString(separator = "."),
                split1.last()
            )
            handlerMethod.addParameter(className, "param$index")
            parameterCount++
        }


        handlerMethod.addParameter(
            ParameterSpec
                .builder(
                    cir, "cir"
                ).build()
        )

        val rawMixinClass = classBuilder(identifier + modNameCapital)
            .addAnnotation(
                AnnotationSpec.builder(mixinAnnotation)
                    .addMember("value", CodeBlock.of("{ $target.class }"))
                    .build()
            )

        if (type == io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.BEFORE_CANCELLABLE) {
            handlerMethod.addCode("\$T control = new \$T;\n", eventControl, eventControl)
        }

        var index = 0
        handlers.forEach {
            val handlerClass = it
            val split1 = handlerClass.split(".")

            val className = ClassName.get(
                split1.dropLast(1).joinToString(separator = "."),
                split1.last()
            )
            rawMixinClass.addField(
                FieldSpec.builder(
                    className,
                    "handler$index"
                ).initializer("new \$T()", className)
                    .build()
            )

            var params = ""

            for (i in 0 until parameterCount) {
                params += "param$i,"
            }
            if (type == io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.BEFORE_CANCELLABLE
            ) {
                params += "control"
            } else {
                params = params.dropLast(1)
            }

            handlerMethod.addCode(
                "handler$index.${methodData[0]}($params);\n"
            )

            index++
        }
        if (type == io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.BEFORE_CANCELLABLE) {
            handlerMethod.addCode("\nif(control.isCancelled()){\n    cir.cancel();\n}\n", eventControl)
        } else if (type == io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.BEFORE && returnName.isNotBlank()) {
            handlerMethod.addCode("\nOptional<\$T> result = control.getOverridenReturnValue()", returnType)
            handlerMethod.addCode(
                "\nif(result.isPresent()){\n    cir.setReturnValue(result.get());\n}\n",
                eventControl
            )
        }

        index = 0

        val mixinClass = rawMixinClass
            .addMethod(
                handlerMethod.build()
            ).build()

        JavaFile
            .builder(packageName, mixinClass)
            .build()
            .writeTo(generatedSourceFolder)

    }
}