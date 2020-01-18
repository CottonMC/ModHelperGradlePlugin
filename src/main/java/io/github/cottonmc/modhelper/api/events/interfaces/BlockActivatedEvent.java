package io.github.cottonmc.modhelper.api.events.interfaces;

import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

@EventDescriptor("io.github.cottonmc.modhelper.api.annotations.events.mixin.BlockBrokenEventMixin")
public interface BlockActivatedEvent {

    void broken(World world, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult, Block block);

    default boolean cancelEvent(World world, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult, Block block) {
        return false;
    }

    /**
     * Return true to play the hand animation. Will not be used, if the event is cancelled.
     */
    default boolean animate(World world, PlayerEntity playerEntity, Hand hand, BlockHitResult blockHitResult, Block block) {
        return false;
    }

}
