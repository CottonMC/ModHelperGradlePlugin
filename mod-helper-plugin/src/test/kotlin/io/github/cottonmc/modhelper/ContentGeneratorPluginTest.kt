package io.github.cottonmc.modhelper

import org.gradle.testkit.runner.GradleRunner
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.nio.file.Path
import java.nio.file.Paths

internal class ContentGeneratorPluginTest {

    @TempDir
    lateinit var directory:Path

    @Disabled
    @Test
    fun `The plugin won't crash gradle on init`() {

        Paths.get(directory.toString(),"build.gradle").toFile().writeText(
            """
                plugins{
                    id "io.github.cottonmc.cotton-mod-helper"
                }
            """.trimIndent()
        )


        val build = GradleRunner
            .create()
            .withProjectDir(directory.toFile())
            .withGradleVersion("5.4").build()

    }
}