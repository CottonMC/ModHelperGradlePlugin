package io.github.cottonmc.modhelper.api.events.mixin;


import io.github.cottonmc.modhelper.api.annotations.modifiers.Before;
import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.BlockExplodedEvent;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 0)
public abstract class BeforeBlockExplodedEventMixin {

    private static HandlerManager<BlockExplodedEvent> handlers = new HandlerManager<>(BlockExplodedEvent.class, Before.class);
    private static HandlerManager<BlockExplodedEvent> clientOnlyHandlers = new HandlerManager<>(BlockExplodedEvent.class, Before.class);

    @Inject(
            at = @At("HEAD"),
            method = "onDestroyedByExplosion"
    )
    private void exploded(World world_1, BlockPos blockPos_1, Explosion explosion_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            handlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, explosion_1), blockPlacedEvent -> blockPlacedEvent.exploded(world_1, blockPos_1, explosion_1));
        } else {
            clientOnlyHandlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, explosion_1), blockPlacedEvent -> blockPlacedEvent.exploded(world_1, blockPos_1, explosion_1));
        }
    }

}
