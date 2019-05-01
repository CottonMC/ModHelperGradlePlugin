package io.github.cottonmc.modhelper.generators

import io.github.cottonmc.contentgenerator.data.ItemTemplateDefinition
import java.util.function.Function

class ItemDataTagGenerator(val modid: String) : Function<ItemTemplateDefinition, String> {

    override fun apply(definition: ItemTemplateDefinition): String {

        return """
       {
  "replace": false,
  "values": [${
        definition.tags
                .map {
                    if (it.split(":").size == 2) {
                        it
                    } else
                        "$modid:$it"
                }
                .map { "\"$it\"" }
                .joinToString(",\n") { it + "\n" }
        }
  ]
}"""
    }
}