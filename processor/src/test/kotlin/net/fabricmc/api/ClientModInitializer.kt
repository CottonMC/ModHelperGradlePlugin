package net.fabricmc.api

interface ClientModInitializer {
    fun onInitializeClient()
}

interface ModInitializer {
    fun onInitialize()
}