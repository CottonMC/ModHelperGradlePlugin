package io.github.cottonmc.contentgenerator.annotations.processor

import io.github.cottonmc.modhelper.api.events.EventDescriptor

@EventDescriptor(
    mixinString = "Lnet/minecraft/block/Block;onPlaced(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
    targetClass = "net.minecraft.block.Block",
    type = EventDescriptor.EventType.BEFORE
)
interface DummyEvent {
    fun handle(thiz:String)
}