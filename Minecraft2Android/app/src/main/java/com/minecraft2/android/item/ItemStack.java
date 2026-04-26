package com.minecraft2.android.item;

public class ItemStack {

    private ItemType type;
    private int count;
    private int durability;
    private String enchantment;

    public ItemStack(ItemType type, int count) {
        this.type = type;
        this.count = Math.min(count, type.getMaxStack());
        this.durability = type.getDurability();
    }

    public boolean canMergeWith(ItemStack other) {
        return other != null && other.type == this.type &&
               other.durability == this.durability &&
               this.count < this.type.getMaxStack();
    }

    public int add(int amount) {
        int canAdd = type.getMaxStack() - count;
        int added = Math.min(amount, canAdd);
        count += added;
        return amount - added;
    }

    public void consume(int amount) {
        count = Math.max(0, count - amount);
    }

    public boolean useDurability(int amount) {
        if (durability <= 0) return true;
        durability -= amount;
        return durability <= 0;
    }

    public boolean isEmpty() { return count <= 0; }
    public boolean isBroken() { return durability <= 0 && type.getDurability() > 0; }
    public ItemStack copy() {
        ItemStack copy = new ItemStack(type, count);
        copy.durability = this.durability;
        copy.enchantment = this.enchantment;
        return copy;
    }

    public ItemType getType() { return type; }
    public int getCount() { return count; }
    public int getDurability() { return durability; }
    public void setCount(int c) { this.count = Math.max(0, Math.min(type.getMaxStack(), c)); }
    public String getEnchantment() { return enchantment; }
    public void setEnchantment(String e) { this.enchantment = e; }

    @Override
    public String toString() {
        return type.getDisplayName() + " x" + count;
    }
}
