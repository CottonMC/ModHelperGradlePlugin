package io.github.cottonmc.modhelper.annotations.test;

import io.github.cottonmc.modhelper.annotations.Entrypoints;
import io.github.cottonmc.modhelper.annotations.Initializer;
import net.fabricmc.api.ModInitializer;

// Should output:
/*
{
  "main": [
    "io.github.cottonmc.modhelper.annotations.test.MultipleInitializers"
  ],
  "client": [
    "io.github.cottonmc.modhelper.annotations.test.MultipleInitializers::initClient"
  ]
}
*/
@Initializer
public class MultipleInitializers implements ModInitializer {
    @Override public void onInitialize() {}

    @Initializer(value = Entrypoints.CLIENT)
    public static void initClient() {}
}
