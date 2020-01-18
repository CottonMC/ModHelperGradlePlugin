package io.github.cottonmc.modhelper.api.events.interfaces;

import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@EventDescriptor("io.github.cottonmc.modhelper.api.annotations.events.mixin.BlockNeighbourUpdateEventMixin")
public interface BlockNeighbourUpdateEvent {

    void update(World world, BlockPos position, Block thiz, BlockPos updatedPosition, boolean boolean_1);

}
