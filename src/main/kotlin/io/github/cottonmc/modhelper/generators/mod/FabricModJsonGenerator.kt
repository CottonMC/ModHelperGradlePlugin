package io.github.cottonmc.modhelper.generators.mod

import com.google.gson.GsonBuilder
import java.util.function.Function

class FabricModJsonGenerator : Function<FabricModJson, String> {
    private val gson = GsonBuilder()
        .registerTypeAdapter(FabricModEntrypoint::class.java, FabricModEntrypoint.Serializer)
        .create()

    override fun apply(t: FabricModJson) = gson.toJson(t)
}
