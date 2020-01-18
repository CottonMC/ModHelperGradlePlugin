package io.github.cottonmc.modhelper.api.events.interfaces;

import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@EventDescriptor("io.github.cottonmc.modhelper.api.annotations.events.mixin.BlockOnLandedUponEventMixin")
public interface EntityLandedOnBlockEvent {

    void entityLanded(World world, BlockPos blockPos, Entity entity, float height);

    default boolean cancelEvent(World world, BlockPos blockPos, Entity entity, float height) {
        return false;
    }

}
