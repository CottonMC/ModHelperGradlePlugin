package io.github.cottonmc.modhelper.api.events.mixin;


import io.github.cottonmc.modhelper.api.events.Identifiable;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

/**
 * adds scripting functionality to the block class.
 */
@Mixin(value = Block.class, priority = 0)
@Implements(@Interface(iface = Identifiable.class, prefix = "indentity$", remap = Interface.Remap.NONE))
public abstract class BlockMixin {

    private Identifier thisId = null;

    /**
     * Dynamically gets the id of this block instance.
     */
    public Identifier indentity$getID() {
        if (thisId == null) {
            thisId = Registry.BLOCK.getId((Block) (Object) this);
        }
        return thisId;
    }
}
