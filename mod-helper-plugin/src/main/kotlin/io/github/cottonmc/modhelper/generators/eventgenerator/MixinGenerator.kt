package io.github.cottonmc.modhelper.generators.eventgenerator

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.squareup.javapoet.*
import com.squareup.javapoet.TypeSpec.classBuilder
import io.github.cottonmc.modhelper.api.events.EventDescriptor
import java.io.File
import java.io.FileReader


class MixinGenerator(
    packageName: String,
    private val generatedFolder: String,
    private val modname: String,
    private val generatedSourceFolder: String
) {

    private val packageName = "$packageName.mixin"
    private val mixinAnnotation = ClassName.get("org.spongepowered.asm.mixin", "Mixin")
    private val injectAnnotation = ClassName.get("org.spongepowered.asm.mixin.injection", "Inject")
    private val atAnnotation = ClassName.get("import org.spongepowered.asm.mixin.injection", "At")
    private val callBackInfo = ClassName.get("org.spongepowered.asm.mixin.injection.callback", "CallbackInfoReturnable")

    fun generate() {
        val eventSource = File("$generatedFolder/eventhandlers.json")
        if (!eventSource.exists())
            return

        val json = Gson().fromJson(FileReader(eventSource), JsonObject::class.java)

        json.keySet().forEach { eventName ->
            val event = json.get(eventName).asJsonObject
            val mixin = event.get("mixinString").asString
            val target = event.get("targetClass").asString
            val type = EventDescriptor.EventType.valueOf(event.get("type").asString)
            val handlers = event.get("handlers").asJsonArray
            val method = event.get("handlerMethod").asString
            val returnName = event.get("returnValue").asString

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
                            returnNameParts.subList(0, eventNameParts.size - 1).joinToString(separator = ".")
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
                EventDescriptor.EventType.BEFORE -> {
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
                EventDescriptor.EventType.AFTER -> {
                    injector.addMember(
                        "cancellable",
                        CodeBlock.of("false")
                    )
                }
                EventDescriptor.EventType.BEFORE_CANCELLABLE -> {
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
                        cir,"cir"
                    ).build()
            )

            val rawMixinClass = classBuilder(identifier + modNameCapital)
                .addAnnotation(
                    AnnotationSpec.builder(mixinAnnotation)
                        .addMember("value", CodeBlock.of("{ $target.class }"))
                        .build()
                )
            var index = 0
            handlers.forEach {
                val handlerClass = it.asString
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
                params = params.dropLast(1)

                handlerMethod.addCode(
                    "handler$index.${methodData[0]}($params);"
                )

                index++
            }
            index = 0

            val mixinClass = rawMixinClass
                .addMethod(
                    handlerMethod.build()
                ).build()

            JavaFile
                .builder(packageName, mixinClass)

                .build()
                .writeTo(File(generatedSourceFolder))
        }
    }
}