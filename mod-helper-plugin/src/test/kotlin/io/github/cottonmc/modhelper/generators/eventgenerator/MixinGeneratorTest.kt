package io.github.cottonmc.modhelper.generators.eventgenerator

import org.apache.commons.io.FileUtils
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junitpioneer.jupiter.TempDirectory
import java.io.File
import java.nio.file.Path

@ExtendWith(TempDirectory::class)
internal class MixinGeneratorTest {

    lateinit var directory: Path
    lateinit var mixinGenerator: MixinGenerator

    @BeforeEach
    fun setUp(@TempDirectory.TempDir directory: Path) {
        this.directory = directory
        mixinGenerator = MixinGenerator("io.github.cottonmc.test", "$directory/generated/", "test", "$directory")
    }

    @Test
    fun `if there is no file, it won't crash`() {
        mixinGenerator.generate()
    }

    @Test
    fun `with one handler in the descriptor`() {
        val source = File(javaClass.getResource("/SimpeHandler2.json").file)
        FileUtils.copyFile(source, File("$directory/generated/eventhandlers.json"))

        mixinGenerator.generate()
        fun listDirectory(file: File) {
            if (file.isDirectory) {
                file.listFiles().forEach {
                    listDirectory(it)
                }
            } else
                println(file)
        }
        listDirectory(directory.toFile())

        File("$directory/io/github/cottonmc/test/mixin/DummyEventTest.java")
            .apply {
                assertTrue(exists(), "the mixin source was not generated")
                println(readText())
            }
    }
}