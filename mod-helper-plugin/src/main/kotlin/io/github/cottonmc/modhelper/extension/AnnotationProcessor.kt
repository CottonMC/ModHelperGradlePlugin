package io.github.cottonmc.modhelper.extension

enum class AnnotationProcessor(internal val configuration: String) {
    JAVA("annotationProcessor"), KAPT("kapt");
}
