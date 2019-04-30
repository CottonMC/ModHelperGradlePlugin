package io.github.cottonmc.contentgenerator.annotations.processor

import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors


fun String.loadLines(): Array<String> {
    return BufferedReader(InputStreamReader(javaClass.getResourceAsStream(this))).lines().collect(
        Collectors.toList()
    ).toTypedArray()
}
