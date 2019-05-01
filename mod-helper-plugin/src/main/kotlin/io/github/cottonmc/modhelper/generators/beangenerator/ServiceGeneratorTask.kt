package io.github.cottonmc.modhelper.generators.beangenerator

import io.github.cottonmc.modhelper.CottonDefaultTask
import io.github.cottonmc.modhelper.api.types.ItemDefinitionType
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ServiceGeneratorTask : CottonDefaultTask() {

    @Input
    var modid:String =""
    @Input
    var dataFolder: String = ""

    @Input
    var packageName:String= project.group.toString()+"."+ project.properties["archives_base_name"]

    @Input
    var output:String=""

    @TaskAction
    fun run() {
        val itemDefinitionClassBuilder =
            ItemDefinitionClassBuilder(packageName)
        val services = ArrayList<String>()

        File("$dataFolder/descriptions/$modid/item/")
                .listFiles()
                .filter {
                    !it.endsWith(".json")
                }.forEach {
                    val json = it.readText()
                    val id = it.nameWithoutExtension

                    val buildFromJson = itemDefinitionClassBuilder.buildFromJson(json, id, modid)
                    File("$output/java/${packageName.replace(".","/")}/${id.uppercaseFirst()}.java").apply {
                        parentFile.mkdirs()
                        writeText(buildFromJson)
                    }
                    services.add("${packageName}.${id.uppercaseFirst()}")
                }
        File("$output/resources/META-INF/services/${ItemDefinitionType::class.java.canonicalName}").apply{
            parentFile.mkdirs()
            this.delete()
            this.createNewFile()
            services.forEach{
                appendText(it+"\n")

            }
        }
    }

    fun String.uppercaseFirst():String{
        return this.substring(0, 1).toUpperCase() + this.substring(1)
    }

}