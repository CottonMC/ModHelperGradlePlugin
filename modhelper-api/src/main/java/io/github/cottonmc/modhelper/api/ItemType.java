package io.github.cottonmc.modhelper.api;

/**
 * The default item types, used to determine what kind of item to generate from the data.
 * */
public enum ItemType implements RawItemType{
    AXE("net.minecraft.items.Axeitem","axe"),
    SHOVEL("net.minecraft.items.Axeitem","shovel"),
    PICKAXE("net.minecraft.items.Axeitem","pickaxe"),
    SWORD("net.minecraft.items.Axeitem","sword"),
    BOW("net.minecraft.items.Axeitem","bow"),
    SHIELD("net.minecraft.items.Axeitem","shield"),
    NONE("net.minecraft.items.Axeitem","item"),
    HOE("net.minecraft.items.Hoeitem","hoe");

    private final String impl;
    private final String name;

    private ItemType(String impl, String name){

        this.impl = impl;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImplemenationClass() {
        return impl;
    }
}
