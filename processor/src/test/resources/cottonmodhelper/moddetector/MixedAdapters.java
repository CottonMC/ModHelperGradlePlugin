package io.github.cottonmc.modhelper.annotations.test;

import io.github.cottonmc.modhelper.api.annotations.initializer.Entrypoints;
import io.github.cottonmc.modhelper.api.annotations.initializer.Initializer;
import net.fabricmc.api.ModInitializer;

// Should output:
/*
{
  "main": [
    "io.github.cottonmc.modhelper.annotations.test.MixedAdapters"
  ],
  "client": [
    {
      "value": "io.github.cottonmc.modhelper.annotations.test.MixedAdapters::initClient",
      "adapter": "kotlin"
    }
  ]
}
*/
@Initializer
public class MixedAdapters implements ModInitializer {
    @Override public void onInitialize() {}

    @Initializer(value = Entrypoints.CLIENT, adapter = "kotlin")
    public static void initClient() {}
}
