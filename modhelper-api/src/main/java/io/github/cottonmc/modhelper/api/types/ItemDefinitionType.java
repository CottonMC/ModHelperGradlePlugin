package io.github.cottonmc.modhelper.api.types;

import io.github.cottonmc.modhelper.api.ItemType;
import io.github.cottonmc.modhelper.api.UseAction;

public interface ItemDefinitionType extends NamedType{
    UseAction getUseAction();

    int getMaxDamage();

    ItemType getType();

    String getMaterialKey();

}
