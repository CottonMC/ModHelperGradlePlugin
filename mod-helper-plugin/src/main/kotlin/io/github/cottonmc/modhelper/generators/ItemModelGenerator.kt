package io.github.cottonmc.modhelper.generators

import io.github.cottonmc.contentgenerator.data.ItemTemplateDefinition
import java.util.function.Function

class ItemModelGenerator(val modid: String) : Function<ItemTemplateDefinition, String> {

    override fun apply(definition: ItemTemplateDefinition): String {
        var template = """
        {
        "parent": "${
        if (definition.modelParent.split(":").size == 2)
            definition.modelParent
        else
            "$modid:${definition.modelParent}"
        }",
        "textures": {"""

        definition.textures.forEachIndexed { index: Int, s: String ->
            template += if (s.split(":").size == 2)
                "\"layer$index\":\"$s\"\n"
            else
                "\"layer$index\":\"$modid:$s\"\n"
        }

        template += """
            }
        }"""

        return template
    }
}