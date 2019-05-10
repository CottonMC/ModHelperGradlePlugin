package io.github.cottonmc.contentgenerator.data

import com.google.gson.Gson
import io.github.cottonmc.modhelper.api.side.Side
import javax.annotation.processing.ProcessingEnvironment
import javax.tools.StandardLocation


/**
 * Reads and writes the mod json from a specific source folder
 * */
object FabricModJsonManager {

    fun read(processingEnvironment: ProcessingEnvironment): FabricModJson {

        val resource = processingEnvironment.filer.getResource(StandardLocation.SOURCE_PATH, "", "/fabric.mod.json")

        return try {
            Gson().fromJson(resource.openReader(true), FabricModJson::class.java)
        } catch (e: Exception) {
            FabricModJson()
        }
    }

    fun update(processingEnvironment: ProcessingEnvironment, modJson: FabricModJson) {
        val resource = processingEnvironment.filer.getResource(StandardLocation.SOURCE_PATH, "", "/fabric.mod.json")

        Gson().toJson(modJson, resource.openWriter())
    }

    fun createMixinFor(processingEnvironment: ProcessingEnvironment, side: Side, classNames: Array<String>) {

        val modJson = read(processingEnvironment)


        val packageMap = HashMap<String, ArrayList<String>>()
        for (className in classNames) {
            val packageName = className.dropLast(1)

            packageMap.getOrPut(packageName, { ArrayList() }).add(className)

        }

        packageMap.forEach {
            val packageName = it.key
            val mixinJson = FabricMixinJson(`package` = packageName)

            when (side) {
                Side.CLIENT -> mixinJson.client.addAll(it.value)
                else -> mixinJson.mixins.addAll(it.value)
            }
            val mixinFileName = "${modJson.id}.${side.name.toLowerCase()}.${packageName.toLowerCase()}.json"

            modJson.addMixin(mixinFileName, side)
            val resource = processingEnvironment.filer.createResource(StandardLocation.SOURCE_PATH, "", mixinFileName)

            return try {
                Gson().toJson(mixinJson, resource.openWriter())
            } catch (e: Exception) {
            }

        }


    }
}