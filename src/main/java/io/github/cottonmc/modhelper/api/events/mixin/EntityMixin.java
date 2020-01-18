package io.github.cottonmc.modhelper.api.events.mixin;

import io.github.cottonmc.functionapi.ServerCommandSourceFactory;
import io.github.cottonmc.functionapi.api.FunctionAPIIdentifier;
import io.github.cottonmc.functionapi.api.commands.CommandSourceExtension;
import io.github.cottonmc.functionapi.api.script.ScriptedObject;
import io.github.cottonmc.functionapi.events.GlobalEventContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


/**
 * generic entity events.
 */
@Mixin(value = Entity.class, priority = 0)
@Implements(@Interface(iface = ScriptedObject.class, prefix = "api_scripted$"))
public abstract class EntityMixin {

    @Shadow
    public World world;
    private Identifier thisId = null;

    @Shadow
    public abstract MinecraftServer getServer();

    @Shadow
    public abstract EntityType<?> getType();

    @Inject(
            at = @At("HEAD"),
            method = "baseTick"
    )
    private void tick(CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            ScriptedObject entity = (ScriptedObject) this;
            GlobalEventContainer.getInstance().executeEvent(entity, "tick", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
        }
    }

    @Inject(
            at = @At("HEAD"),
            method = "onSwimmingStart"
    )
    private void swimStart(CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            ScriptedObject entity = (ScriptedObject) this;
            GlobalEventContainer.getInstance().executeEvent(entity, "swim_start", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
        }
    }

    @Inject(at = @At("HEAD"), method = "damage",
            cancellable = true)
    private void damagedBEFORE(DamageSource damageSource_1, float float_1, CallbackInfoReturnable<Boolean> cir) {
        ScriptedObject entity = (ScriptedObject) this;

        if (world instanceof ServerWorld) {
            ServerCommandSource serverCommandSource = GlobalEventContainer.getInstance().executeEventBlocking(entity, "before/damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));

            if (((CommandSourceExtension) serverCommandSource).isCancelled()) {
                cir.cancel();
            }
        }
        GlobalEventContainer.getInstance().executeEvent(entity, "damage", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
    }

    @Inject(at = @At("HEAD"), method = "onStruckByLightning")
    private void onStruckByLightning(LightningEntity lightningEntity_1, CallbackInfo ci) {
        if (world instanceof ServerWorld) {
            ScriptedObject entity = (ScriptedObject) this;
            GlobalEventContainer.getInstance().executeEvent(entity, "struck_by_lightning", ServerCommandSourceFactory.INSTANCE.create(getServer(), (ServerWorld) world, (Entity) (Object) this));
        }
    }


    public FunctionAPIIdentifier api_scripted$getEventID() {
        if (thisId == null) {
            if ((Entity) (Object) this instanceof PlayerEntity) {
                thisId = new Identifier("player");
            } else
                thisId = Registry.ENTITY_TYPE.getId(this.getType());
        }
        return (FunctionAPIIdentifier) thisId;
    }

    public String api_scripted$getEventType() {
        return "entity";
    }
}
