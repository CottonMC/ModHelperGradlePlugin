package io.github.cottonmc.modhelper.api.events.interfaces;

import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@EventDescriptor("io.github.cottonmc.modhelper.api.annotations.events.mixin.BlockPlacedEventMixin")
public interface BlockPlacedEvent {

    void place(World world, BlockPos blockPos, BlockState blockState, LivingEntity placer, ItemStack placedStack);

    default boolean cancelEvent(World world, BlockPos blockPos, BlockState blockState, LivingEntity placer, ItemStack placedStack) {
        return false;
    }

}
