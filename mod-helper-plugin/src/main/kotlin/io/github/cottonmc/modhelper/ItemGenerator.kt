package io.github.cottonmc.modhelper

import io.github.cottonmc.contentgenerator.data.ItemTemplateDefinition
import io.github.cottonmc.contentgenerator.data.ToolMaterialDefinition
import io.github.cottonmc.modhelper.generators.ItemDataTagGenerator
import io.github.cottonmc.modhelper.generators.ItemDescriptionGenerator
import io.github.cottonmc.modhelper.generators.ItemModelGenerator
import io.github.cottonmc.modhelper.generators.ToolMaterialDescriptionGenerator
import io.github.cottonmc.contentgenerator.data.ItemDefinition
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import java.io.File

open class ItemGenerator : CottonDefaultTask() {

    @Internal
    val definitions: MutableMap<String, ItemTemplateDefinition> = HashMap()
    @Internal
    val toolMaterialDefinitions: MutableMap<String, ToolMaterialDefinition> = HashMap()
    @Input
    var generatedResourceRoot = "./build/generated/resources"
    @Input
    var generatedDescriptionRoot = generatedResourceRoot
    @Input
    var modid: String = "minecraft"

    @TaskAction
    fun run() {

        val itemModelGenerator = ItemModelGenerator(modid)
        val itemDataTagGenerator = ItemDataTagGenerator(modid)
        val itemDescriptionGenerator = ItemDescriptionGenerator(modid)
        val toolMaterialDescriptionGenerator =
            ToolMaterialDescriptionGenerator(modid)

        definitions.forEach {
            //generate item model file
            val model = itemModelGenerator.apply(it.value)
            File("$generatedResourceRoot/assets/$modid/models/item/${it.key}.json").apply {
                parentFile.mkdirs()
                writeText(model)
            }

            //generate tag data file
            val data = itemDataTagGenerator.apply(it.value)

            File("$generatedResourceRoot/data/$modid/tags/item/${it.key}.json").apply {
                parentFile.mkdirs()
                writeText(data)
            }

            val description = itemDescriptionGenerator.apply(ItemDefinition(it.key, it.value))

            File("$generatedDescriptionRoot/descriptions/$modid/item/${it.key}.json").apply {
                parentFile.mkdirs()
                writeText(description)
            }
        }

        toolMaterialDefinitions.forEach {
            val description = toolMaterialDescriptionGenerator.apply(it.value)
            File("$generatedDescriptionRoot/descriptions/$modid/toolmaterial/${it.key}.json").apply {
                parentFile.mkdirs()
                writeText(description)
            }
        }
    }

    @Input
    fun addDefinition(name: String, definition: ItemTemplateDefinition) {
        definitions[name] = definition
    }

    @Input
    fun addToolMaterialDefinition(name: String, definition: ToolMaterialDefinition) {
        toolMaterialDefinitions[name] = definition
    }
}