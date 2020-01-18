package io.github.cottonmc.modhelper.api.events.mixin;


import io.github.cottonmc.modhelper.api.annotations.modifiers.Before;
import io.github.cottonmc.modhelper.api.events.HandlerManager;
import io.github.cottonmc.modhelper.api.events.interfaces.BlockPlacedEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, priority = 0)
public abstract class BeforeBlockPlacedEventMixin {

    private static HandlerManager<BlockPlacedEvent> handlers = new HandlerManager<>(BlockPlacedEvent.class, Before.class);
    private static HandlerManager<BlockPlacedEvent> clientOnlyHandlers = new HandlerManager<>(BlockPlacedEvent.class, Before.class);

    @Inject(
            at = @At("HEAD"),
            method = "onPlaced"
    )
    private void place(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1, CallbackInfo ci) {
        if (world_1 instanceof ServerWorld) {
            handlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1), blockPlacedEvent -> blockPlacedEvent.place(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1));
        } else {
            clientOnlyHandlers.executeEvent(event -> event.cancelEvent(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1), blockPlacedEvent -> blockPlacedEvent.place(world_1, blockPos_1, blockState_1, livingEntity_1, itemStack_1));
        }
    }
}
