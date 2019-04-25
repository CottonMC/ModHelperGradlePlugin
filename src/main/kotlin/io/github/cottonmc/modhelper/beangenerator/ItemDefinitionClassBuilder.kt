package io.github.cottonmc.modhelper.beangenerator

import com.google.gson.Gson
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.TypeSpec.classBuilder
import io.github.cottonmc.modhelper.generators.ItemDescriptionGenerator
import io.github.cottonmc.contentgenerator.data.ItemType
import io.github.cottonmc.contentgenerator.data.UseAction
import io.github.cottonmc.contentgenerator.data.types.ItemDefinitionType
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeName
import java.io.StringWriter
import javax.lang.model.element.Modifier


class ItemDefinitionClassBuilder(val packageName: String) {

    fun buildFromJson(json: String, name: String, modid: String): String {
        val fromJson = Gson().fromJson(json, ItemDescriptionGenerator.ItemDescription::class.java)


        val build = classBuilder(name.substring(0, 1).toUpperCase() + name.substring(1))
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ItemDefinitionType::class.java)
                .addMethod(MethodSpec.methodBuilder("getMaterialKey")
                        .addAnnotation(Override::class.java)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(String::class.java)
                        .addCode("return \$S;", fromJson.material)
                        .build()
                )
                .addMethod(MethodSpec.methodBuilder("getUseAction")
                        .addAnnotation(Override::class.java)
                        .addModifiers(Modifier.PUBLIC)
                        .returns(UseAction::class.java)
                        .addCode("return \$T.\$L;", UseAction::class.java, fromJson.useAction)
                        .build()
                )
                .addMethod(MethodSpec.methodBuilder("getMaxDamage")
                        .addAnnotation(Override::class.java)
                        .returns(TypeName.INT)
                        .addModifiers(Modifier.PUBLIC)
                        .addCode("return \$L;", fromJson.maxDamage)
                        .build()
                )
                .addMethod(MethodSpec.methodBuilder("getType")
                        .addAnnotation(Override::class.java)
                        .returns(ItemType::class.java)
                        .addModifiers(Modifier.PUBLIC)
                        .addCode("return \$T.\$L;", ItemType::class.java, fromJson.type)
                        .build()
                )
                .addMethod(MethodSpec.methodBuilder("getName")
                        .addAnnotation(Override::class.java)
                        .returns(String::class.java)
                        .addModifiers(Modifier.PUBLIC)
                        .addCode("return \$S;", "$modid:$name")
                        .build()
                ).build()
        val javaFile = JavaFile.builder(packageName, build)
                .build()
        val out = StringWriter()
        javaFile.writeTo(out)

        return out.toString()

    }

}