package io.github.cottonmc.contentgenerator.data

data class FabricMixinJson(
    val required: Boolean = true,
    val `package`: String

) {
    val compatibilityLevel = "JAVA_8"
    val injectors = mapOf(
        Pair("defaultRequire", 1)
    )
    var mixins: ArrayList<String> = ArrayList()
    var client: ArrayList<String> = ArrayList()
}