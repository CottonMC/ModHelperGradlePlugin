package io.github.cottonmc.modhelper.api.events.mixin;

import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.DatapackReloadEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MinecraftServer.class)
public abstract class DatapackReloadEventMixin {
    private static HandlerManager<DatapackReloadEvent> handlers = new HandlerManager<>(DatapackReloadEvent.class);

    @Inject(at = @At("TAIL"), method = "reloadDataPacks")
    private void reload(LevelProperties levelProperties_1, CallbackInfo ci) {
        handlers.getHandlers().forEach(DatapackReloadEvent::reload);
    }

}
