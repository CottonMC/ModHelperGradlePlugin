package io.github.cottonmc.modhelper.api.events.mixin;


import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.EntityLandedOnBlockEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 0)
public abstract class BlockOnLandedUponEventMixin {

    private static HandlerManager<EntityLandedOnBlockEvent> handlers = new HandlerManager<>(EntityLandedOnBlockEvent.class);
    private static HandlerManager<EntityLandedOnBlockEvent> clientOnlyHandlers = new HandlerManager<>(EntityLandedOnBlockEvent.class);


    @Inject(
            at = @At("TAIL"),
            method = "onLandedUpon", cancellable = true
    )
    private void onLandedUpon(World world_1, BlockPos blockPos_1, Entity entity_1, float float_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            handlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, entity_1, float_1), blockPlacedEvent -> blockPlacedEvent.entityLanded(world_1, blockPos_1, entity_1, float_1));
        } else {
            clientOnlyHandlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, entity_1, float_1), blockPlacedEvent -> blockPlacedEvent.entityLanded(world_1, blockPos_1, entity_1, float_1));
        }
    }

}
