package io.github.cottonmc.modhelper.extension

open class ModHelperExtension {
    var modid: String = ""
    var modName: String = ""
    var version: String = ""
    var description: String = ""

    /**
     * The annotation processing backend.
     */
    var annotationProcessor: AnnotationProcessor = AnnotationProcessor.JAVA

    /**
     * The output path (relative to the project root) of the generated resources.
     */
    var generatedResourceOutputPath: String = "build/cotton/"

    /**
     * If true, enables debug features in `mod-helper`.
     *
     * List of debug features:
     *
     * - Including internal build files in the output JAR
     */
    var debug: Boolean = false
}
