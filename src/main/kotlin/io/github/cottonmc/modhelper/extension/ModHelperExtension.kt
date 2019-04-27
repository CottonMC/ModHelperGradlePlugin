package io.github.cottonmc.modhelper.extension

open class ModHelperExtension {
    var modid: String = ""
    var modname: String = ""
    var description: String = ""
    var annotationProcessor: AnnotationProcessor = AnnotationProcessor.JAVA
    var cottonGeneratedOutputPath: String = "build/cotton/"
    var debug: Boolean = false
}
