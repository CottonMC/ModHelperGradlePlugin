package io.github.cottonmc.modhelper.api.events.mixin;


import io.github.cottonmc.modhelper.api.annotations.modifiers.Before;
import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.BlockBrokenEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 0)
public abstract class BeforeBlockBrokenEventMixin {

    private static HandlerManager<BlockBrokenEvent> handlers = new HandlerManager<>(BlockBrokenEvent.class, Before.class);
    private static HandlerManager<BlockBrokenEvent> clientOnlyHandlers = new HandlerManager<>(BlockBrokenEvent.class, Before.class);


    @Inject(
            at = @At("HEAD"),
            method = "onBroken",
            cancellable = true
    )
    private void brokenBefore(IWorld world_1, BlockPos blockPos_1, BlockState blockState_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            handlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, blockState_1), blockPlacedEvent -> blockPlacedEvent.broken(world_1, blockPos_1, blockState_1), ci);
        } else {
            clientOnlyHandlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, blockState_1), blockPlacedEvent -> blockPlacedEvent.broken(world_1, blockPos_1, blockState_1));
        }
    }

}
