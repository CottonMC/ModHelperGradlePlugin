package io.github.cottonmc.modhelper.api.events.mixin;

import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.BlockNeighbourUpdateEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {BlockState.class}, priority = 0)
public abstract class BlockNeighbourUpdateEventMixin {

    private static HandlerManager<BlockNeighbourUpdateEvent> handlers = new HandlerManager<>(BlockNeighbourUpdateEvent.class);
    private static HandlerManager<BlockNeighbourUpdateEvent> clientOnlyHandlers = new HandlerManager<>(BlockNeighbourUpdateEvent.class);

    @Shadow
    public abstract Block getBlock();


    @Inject(at = @At("TAIL"), method = "neighborUpdate")
    private void neighborUpdate(World world, BlockPos blockPos_1, Block block_1, BlockPos blockPos_2, boolean boolean_1, CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            handlers.executeEvent(
                    handler -> {
                        handler.update(world, blockPos_1, block_1, blockPos_2, boolean_1);
                    }
            );
        } else {
            clientOnlyHandlers.executeEvent(
                    handler -> {
                        handler.update(world, blockPos_1, block_1, blockPos_2, boolean_1);
                    }
            );
        }
    }
}
