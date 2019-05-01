package io.github.cottonmc.modhelper.generators

import com.google.gson.Gson
import io.github.cottonmc.contentgenerator.data.ItemDefinition
import io.github.cottonmc.modhelper.api.ItemType
import java.util.function.Function

class ItemDescriptionGenerator(val modid: String) : Function<ItemDefinition, String> {

    override fun apply(definition: ItemDefinition): String {
       return Gson().toJson(ItemDescription(definition))
    }

    data class ItemDescription(
            val name: String,
            val maxDamage: Int = 0,
            val type: ItemType = ItemType.NONE,
            val material: String = ""
    ) {
        constructor(definition: ItemDefinition) : this(
                name = {
                    val split = definition.name.split(":")
                    if (split.size == 2)
                        split[1]
                    definition.name
                }(),
                maxDamage = definition.definition.maxDamage,
                type=definition.definition.type,
                material = definition.definition.materialKey
        )
    }
}