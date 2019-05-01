package io.github.cottonmc.modhelper.api.events;


import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static io.github.cottonmc.modhelper.api.events.EventDescriptor.EventType.BEFORE;

@EventDescriptor(
        mixinString = "Lnet/minecraft/block/Block;onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
        targetClass = "net.minecraft.block.Block",
        type = BEFORE,
        cancelleable = true
)
public interface BlockPlacedEvent {

    void onPlaced(World world, BlockPos position, BlockState blockstate, @Nullable LivingEntity placer, ItemStack placedStack,EventControl eventControl);

}
