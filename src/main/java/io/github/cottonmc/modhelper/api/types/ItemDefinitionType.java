package io.github.cottonmc.modhelper.api.types;

import io.github.cottonmc.modhelper.api.ItemType;

public interface ItemDefinitionType extends NamedType{

    int getMaxDamage();

    ItemType getType();

    String getMaterialKey();

}
