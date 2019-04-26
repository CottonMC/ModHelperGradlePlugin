package io.github.cottonmc.contentgenerator.annotations

// TODO: Convert to Java
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
annotation class Initializer(
    val entrypointType: String = "main",
    val adapter: String = ""
)
