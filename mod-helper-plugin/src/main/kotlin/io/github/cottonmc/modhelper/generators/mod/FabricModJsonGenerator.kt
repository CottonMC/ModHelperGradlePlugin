package io.github.cottonmc.modhelper.generators.mod

import com.google.gson.GsonBuilder
import java.util.function.Function

class FabricModJsonGenerator : Function<FabricModJson, String> {
    internal val gson = GsonBuilder()
        .registerTypeAdapter(FabricModEntrypoint::class.java, FabricModEntrypoint.JsonAdapter)
        .registerTypeAdapter(FabricEntrypointContainer::class.java, FabricEntrypointContainer.JsonAdapter)
        .registerTypeAdapter(FabricModJson::class.java, FabricModJson.Serializer)
        .create()

    override fun apply(t: FabricModJson) = gson.toJson(t)
}
