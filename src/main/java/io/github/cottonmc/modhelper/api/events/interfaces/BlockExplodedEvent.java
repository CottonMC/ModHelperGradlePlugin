package io.github.cottonmc.modhelper.api.events.interfaces;

import io.github.cottonmc.modhelper.api.annotations.initializer.EventDescriptor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

@EventDescriptor("io.github.cottonmc.modhelper.api.events.mixin.BlockExplodedEventMixin")
public interface BlockExplodedEvent {
    void exploded(World world, BlockPos blockPos, Explosion explosion);

    default boolean cancelEvent(World world, BlockPos blockPos, Explosion explosion) {
        return false;
    }
}
