package io.github.cottonmc.modhelper.api.events.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(value = {net.minecraft.block.BlockState.class}, priority = 0)
public abstract class BlockStateMixin {

    @Shadow
    public abstract Block getBlock();


    @Inject(at = @At("TAIL"), method = "onEntityCollision")
    private void onEntityCollision(World world_1, BlockPos blockPos_1, Entity entity_1, CallbackInfo ci) {
        if (!world_1.isClient())
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "entity_collided", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockPos_1, entity_1));
    }

    @Inject(at = @At("TAIL"), method = "onProjectileHit")
    private void onProjectileHit(World world_1, BlockState blockState_1, BlockHitResult blockHitResult_1, Entity entity_1, CallbackInfo ci) {
        if (!world_1.isClient())
            GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "projectile_hit", ServerCommandSourceFactory.INSTANCE.create(world_1.getServer(), (ServerWorld) world_1, this.getBlock(), blockHitResult_1.getBlockPos(), entity_1));
    }

    @Inject(at = @At("TAIL"), method = "scheduledTick")
    private void onScheduledTick(ServerWorld serverWorld, BlockPos blockPos, Random random, CallbackInfo ci) {
        GlobalEventContainer.getInstance().executeEvent((ScriptedObject) this.getBlock(), "tick", ServerCommandSourceFactory.INSTANCE.create(serverWorld.getServer(), serverWorld, this.getBlock(), blockPos))/* Fired on every scheduled tick.*/;
    }

}
