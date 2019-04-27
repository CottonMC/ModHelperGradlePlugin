package io.github.cottonmc.modhelper.extension

open class ModHelperExtension {
    var modid: String = ""
    var modName: String = ""
    var version: String = ""
    var description: String = ""
    var annotationProcessor: AnnotationProcessor = AnnotationProcessor.JAVA
    var version:String=""
    var cottonGeneratedOutputPath: String = "build/cotton/"
    var debug: Boolean = false
}
