package io.github.cottonmc.modhelper.api.events.interfaces;

import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

@EventDescriptor("io.github.cottonmc.modhelper.api.annotations.events.mixin.BlockBrokenEventMixin")
public interface BlockBrokenEvent {

    void broken(IWorld world, BlockPos blockPos, BlockState blockState);

    default boolean cancelEvent(IWorld world, BlockPos blockPos, BlockState blockState) {
        return false;
    }

}
