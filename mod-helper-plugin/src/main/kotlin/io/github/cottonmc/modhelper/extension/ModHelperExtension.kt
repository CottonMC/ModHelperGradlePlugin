package io.github.cottonmc.modhelper.extension

open class ModHelperExtension {
    var modid: String = ""
    var modName: String = ""
    var version: String = ""
    var description: String = ""
    var defaultpackage=""

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
    /**
     * if true, the fabric information will be gathered from the code, and be processed.
     *
     * */
    var enableFabricDescriptor:Boolean = true
    /**
     * if true,sponge mixins will be detected, and registered to fabric automatically.
     *
     * */
    var enableMixinProcessing = true

    /**
     * If set to true, the cotton modhelper api features will be used, and processed.
     *
     * Features:
     * - every @Subscribe annotation will be gathered, and recorded.
     * - fabric mixins will be generated to inject the event handlers into the game.
     * @see io.github.cottonmc.modhelper.api.events.Subscribe
     *
     * */
    var enableCottonEventsModule=false

    /**
     * if set to true, a forge toml file will be generated.
     * */
    var enableForgeDescriptor=false
}
