package io.github.cottonmc.modhelper.api.events.mixin;


import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.BlockSteppedOnEvent;
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
public abstract class BeforeBlockSteppedOnEventMixin {

    private static HandlerManager<BlockSteppedOnEvent> handlers = new HandlerManager<>(BlockSteppedOnEvent.class);
    private static HandlerManager<BlockSteppedOnEvent> clientOnlyHandlers = new HandlerManager<>(BlockSteppedOnEvent.class);

    @Inject(at = @At("HEAD"), method = "onSteppedOn")
    private void onSteppedOn(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            handlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, entity_1), blockPlacedEvent -> blockPlacedEvent.steppedOn(world_1, blockPos_1, entity_1));
        } else {
            clientOnlyHandlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, entity_1), blockPlacedEvent -> blockPlacedEvent.steppedOn(world_1, blockPos_1, entity_1));
        }
    }

}
