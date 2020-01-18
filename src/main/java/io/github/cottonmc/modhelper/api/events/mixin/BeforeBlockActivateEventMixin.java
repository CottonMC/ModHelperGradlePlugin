package io.github.cottonmc.modhelper.api.events.mixin;

import io.github.cottonmc.modhelper.api.annotations.modifiers.Before;
import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.BlockActivatedEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {BlockState.class}, priority = 0)
public abstract class BeforeBlockActivateEventMixin {

    private static HandlerManager<BlockActivatedEvent> handlers = new HandlerManager<>(BlockActivatedEvent.class, Before.class);
    private static HandlerManager<BlockActivatedEvent> clientOnlyHandlers = new HandlerManager<>(BlockActivatedEvent.class, Before.class);

    @Shadow
    public abstract Block getBlock();

    @Inject(
            at = @At("HEAD"),
            method = "onUse",
            cancellable = true
    )
    private void activate(World world, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult, CallbackInfoReturnable<Boolean> cir) {
        if (world instanceof ServerWorld) {
            handlers.executeEvent(
                    event -> event.cancelEvent(world, playerEntity, hand, blockHitResult, getBlock()),
                    event -> event.broken(world, playerEntity, hand, blockHitResult, getBlock()),
                    handler -> {
                        if (handler.animate(world, playerEntity, hand, blockHitResult, getBlock())) {
                            cir.setReturnValue(true);
                        }
                        return false;
                    }
            );
        } else {
            clientOnlyHandlers.executeEvent(
                    event -> event.cancelEvent(world, playerEntity, hand, blockHitResult, getBlock()),
                    event -> event.broken(world, playerEntity, hand, blockHitResult, getBlock()),
                    handler -> {
                        if (handler.animate(world, playerEntity, hand, blockHitResult, getBlock())) {
                            cir.setReturnValue(true);
                        }
                        return false;
                    }
            );
        }

    }
}
