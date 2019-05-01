package io.github.cottonmc.modhelper.generators

import com.google.gson.Gson
import io.github.cottonmc.contentgenerator.data.ToolMaterialDefinition
import java.util.function.Function

class ToolMaterialDescriptionGenerator(val modid: String) : Function<ToolMaterialDefinition, String> {
    override fun apply(definition: ToolMaterialDefinition): String {
        return Gson().toJson(definition)
    }
}