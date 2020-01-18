package io.github.cottonmc.modhelper.annotations.test;

import io.github.cottonmc.modhelper.api.annotations.initializer.Initializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.ModInitializer;

// Should output:
/*
{
  "main": [
    "io.github.cottonmc.modhelper.annotations.test.EntrypointDetection"
  ],
  "client": [
    "io.github.cottonmc.modhelper.annotations.test.EntrypointDetection$Client"
  ]
}
*/
@Initializer
public class EntrypointDetection implements ModInitializer {
    @Override public void onInitialize() {}

    @Initializer
    public static class Client implements ClientModInitializer {
        @Override public void onInitializeClient() {}
    }

    @Initializer
    public static class Server implements DedicatedServerModInitializer {
        @Override public void onInitializeServer() {}
    }
}
