package io.github.cottonmc.contentgenerator.annotations.processor

import com.google.testing.compile.Compilation
import com.google.testing.compile.Compiler
import com.google.testing.compile.JavaFileObjects
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors
import javax.tools.JavaFileObject


fun String.loadLines(): Array<String> {
    return BufferedReader(InputStreamReader(javaClass.getResourceAsStream(this))).lines().collect(
        Collectors.toList()
    ).toTypedArray()
}

fun compilation(vararg objects: JavaFileObject): Compilation? {
    val list = objects.toMutableList()
    return  Compiler.javac().withProcessors(CottonAnnotationProcessor()).compile(
            *list.toTypedArray()
        )
}