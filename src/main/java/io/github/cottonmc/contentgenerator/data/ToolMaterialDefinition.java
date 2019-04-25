package io.github.cottonmc.contentgenerator.data;

public class ToolMaterialDefinition {
    private String name;
    private int durability;
    private float breakingSpeed;
    private float attackDamage;
    private int miningLevel;
    private int enchantability;
    private Ingredient repairItem;

    public ToolMaterialDefinition(String name,
                                  int durability,
                                  float breakingSpeed,
                                  float attackDamage,
                                  int miningLevel,
                                  int enchantability,
                                  Ingredient repairItem) {

        this.name = name;
        this.durability = durability;
        this.breakingSpeed = breakingSpeed;
        this.attackDamage = attackDamage;
        this.miningLevel = miningLevel;
        this.enchantability = enchantability;
        this.repairItem = repairItem;
    }

    public String getName() {
        return name;
    }

    public int getDurability() {
        return durability;
    }

    public float getBreakingSpeed() {
        return breakingSpeed;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public int getEnchantability() {
        return enchantability;
    }

    public Ingredient getRepairItem() {
        return repairItem;
    }
}
