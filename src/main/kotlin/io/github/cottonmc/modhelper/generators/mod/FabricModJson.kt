package io.github.cottonmc.modhelper.generators.mod

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class FabricModJson {
    var schemaVersion: Int = 1
    var id: String = ""
    var name: String = ""
    var version: String = ""
    var icon: String = ""
    var description: String = ""
    var license: String = ""
    var contact: FabricModContact = FabricModContact()
    var environment: Array<String> = arrayOf("*")
    var entrypoints: FabricEntrypointContainer = FabricEntrypointContainer()
    var mixins: Array<String> = emptyArray()
    var depends: Map<String, String> = emptyMap()

    internal object Serializer : JsonSerializer<FabricModJson> {
        private fun JsonObject.addPropertyIfNotEmpty(key: String, value: String) {
            if (value.isNotEmpty()) {
                addProperty(key, value)
            }
        }

        override fun serialize(src: FabricModJson, typeOfSrc: Type?, context: JsonSerializationContext) =
            JsonObject().apply {
                with(src) {
                    addProperty("schemaVersion", schemaVersion)
                    addProperty("id", id)
                    addProperty("version", version)
                    addPropertyIfNotEmpty("name", name)
                    addPropertyIfNotEmpty("icon", icon)
                    addPropertyIfNotEmpty("description", description)
                    addPropertyIfNotEmpty("license", license)
                    add("contact", context.serialize(contact))
                    add("environment", context.serialize(environment))
                    add("mixins", context.serialize(mixins))
                    add("depends", context.serialize(depends))

                    if (entrypoints.map.isNotEmpty()) {
                        add("entrypoints", context.serialize(entrypoints))
                    }
                }
            }
    }
}

class FabricModContact {
    var sources: String = ""
}

class FabricEntrypointContainer @JvmOverloads constructor(val map: Map<String, List<FabricModEntrypoint>> = emptyMap()) {
    internal object JsonAdapter : JsonSerializer<FabricEntrypointContainer>, JsonDeserializer<FabricEntrypointContainer> {
        override fun serialize(src: FabricEntrypointContainer, typeOfSrc: Type?, context: JsonSerializationContext) =
            context.serialize(src.map)

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext
        ) = FabricEntrypointContainer(
            context.deserialize<Map<String, List<FabricModEntrypoint>>>(
                json, (object : TypeToken<Map<String, List<FabricModEntrypoint>>>() {}).type
            )
        )
    }
}

class FabricModEntrypoint {
    var adapter: String = ""
    var value: String = ""

    internal object JsonAdapter : JsonSerializer<FabricModEntrypoint>, JsonDeserializer<FabricModEntrypoint> {
        override fun serialize(src: FabricModEntrypoint, typeOfSrc: Type?, context: JsonSerializationContext) =
            if (src.adapter.isEmpty()) {
                JsonPrimitive(src.value)
            } else {
                JsonObject().apply {
                    addProperty("value", src.value)
                    addProperty("adapter", src.adapter)
                }
            }

        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext
        ) = when (json) {
            is JsonObject -> FabricModEntrypoint().apply {
                value = json["value"].asString

                if (json.has("adapter")) {
                    adapter = json["adapter"].asString
                }
            }

            is JsonPrimitive -> FabricModEntrypoint().apply {
                value = json.asString
            }

            else -> throw IllegalArgumentException("Incompatible JSON element: $json")
        }
    }
}
