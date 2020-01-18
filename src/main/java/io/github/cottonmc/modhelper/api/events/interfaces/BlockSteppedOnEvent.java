package io.github.cottonmc.modhelper.api.events.interfaces;

import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

@EventDescriptor("io.github.cottonmc.modhelper.api.annotations.events.mixin.BlockSteppedOnEventMixin")
public interface BlockSteppedOnEvent {

    void steppedOn(World world, BlockPos blockPos, Entity entity);

    default boolean cancelEvent(IWorld world, BlockPos blockPos, Entity entity) {
        return false;
    }

}
