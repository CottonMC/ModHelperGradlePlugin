package io.github.cottonmc.contentgenerator.example;

import io.github.cottonmc.contentgenerator.annotations.Initializer;

public class JavaMod {
    @Initializer
    public static void init() {

    }

    @Initializer(entrypointType = "client")
    public static void initClient() {

    }
}
