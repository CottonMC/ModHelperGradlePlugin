package io.github.cottonmc.modhelper.generators.mod

import com.google.gson.*
import java.lang.reflect.Type

class FabricModJson {
    var schemaVersion: String = "1"
    var id: String = ""
    var name: String = ""
    var version: String = ""
    var icon: String = ""
    var description: String = ""
    var license: String = ""
    var contact: FabricModContact = FabricModContact()
    var environment: Array<String> = arrayOf("*")
    var entrypoints: Map<String, Array<FabricModEntrypoint>> = emptyMap()
    var mixins: Array<String> = emptyArray()
    var depends: Map<String, String> = emptyMap()
}

class FabricModContact {
    var sources: String = ""
}

class FabricModEntrypoint {
    var adapter: String = ""
    var value: String = ""

    internal object Serializer : JsonSerializer<FabricModEntrypoint> {
        override fun serialize(src: FabricModEntrypoint, typeOfSrc: Type?, context: JsonSerializationContext) =
            if (src.adapter.isEmpty()) {
                JsonPrimitive(src.value)
            } else {
                JsonObject().apply {
                    addProperty("value", src.value)
                    addProperty("adapter", src.adapter)
                }
            }
    }
}
