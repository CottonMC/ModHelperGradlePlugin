package io.github.cottonmc.contentgenerator.annotations.processor

import io.github.cottonmc.modhelper.api.annotations.events.EventDescriptor
import java.util.*

@io.github.cottonmc.modhelper.api.annotations.EventDescriptor(
    mixinString = "Lnet/minecraft/block/Block;onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
    targetClass = "net.minecraft.block.Block",
    type = io.github.cottonmc.modhelper.api.annotations.EventDescriptor.EventType.BEFORE
)
interface DummyEvent {
    fun handle(thiz:String,random:Random)
}