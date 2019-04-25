package io.github.cottonmc.contentgenerator.data;

public class Ingredient {
    private String tag;
    private String item;

    public String getTag() {
        return tag;
    }

    public Ingredient setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getItem() {
        return item;
    }

    public Ingredient setItem(String item) {
        this.item = item;
        return this;
    }
}
