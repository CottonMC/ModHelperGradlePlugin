package io.github.cottonmc.contentgenerator.data;

import java.util.List;

public class ItemTemplateDefinition {

    private UseAction useAction = UseAction.NORMAL;
    private List<String> textures;
    private List<String> tags;
    private String modelParent;
    private int colorOverride = 0;
    private int maxDamage = 0;
    private ItemType type = ItemType.NONE;
    private String materialKey="";


    public ItemTemplateDefinition(List<String> textures, List<String> tags, String modelParent) {
        this.textures = textures;
        this.tags = tags;
        this.modelParent = modelParent;
    }

    public UseAction getUseAction() {
        return useAction;
    }

    public List<String> getTextures() {
        return textures;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getModelParent() {
        return modelParent;
    }

    public int getColorOverride() {
        return colorOverride;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public ItemTemplateDefinition setUseAction(UseAction useAction) {
        this.useAction = useAction;
        return this;

    }

    public ItemTemplateDefinition setColorOverride(int colorOverride) {
        this.colorOverride = colorOverride;
        return this;

    }

    public ItemTemplateDefinition setMaxDamage(int maxDamage) {
        this.maxDamage = maxDamage;
        return this;

    }

    public ItemType getType() {
        return type;
    }

    public ItemTemplateDefinition setType(ItemType type) {
        this.type = type;
        return this;
    }

    public String getMaterialKey() {
        return materialKey;
    }

    public ItemTemplateDefinition setMaterialKey(String materialKey) {
        this.materialKey = materialKey;
        return this;
    }
}
