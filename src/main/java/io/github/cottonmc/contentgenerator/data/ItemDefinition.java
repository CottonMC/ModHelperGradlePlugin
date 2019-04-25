package io.github.cottonmc.contentgenerator.data;

public class ItemDefinition {

   private String name;
    private ItemTemplateDefinition definition;


    public ItemDefinition(String name, ItemTemplateDefinition definition) {
        this.name = name;
        this.definition = definition;
    }

    public ItemTemplateDefinition getDefinition() {
        return definition;
    }

    public String getName() {
        return name;
    }

}
