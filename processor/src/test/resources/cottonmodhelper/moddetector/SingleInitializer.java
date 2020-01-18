package io.github.cottonmc.modhelper.annotations.test;

import io.github.cottonmc.modhelper.api.annotations.initializer.Initializer;
import net.fabricmc.api.ModInitializer;

// Should output:
/*
{
  "main": [
    "io.github.cottonmc.modhelper.annotations.test.SingleInitializer"
  ]
}
*/
@Initializer
public class SingleInitializer implements ModInitializer {
    @Override public void onInitialize() {}
}
