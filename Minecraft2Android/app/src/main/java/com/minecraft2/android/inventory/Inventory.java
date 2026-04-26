package com.minecraft2.android.inventory;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import java.util.Arrays;

public class Inventory {

    private final ItemStack[] slots;
    private final int size;
    private static final int HOTBAR_SIZE = 9;

    public Inventory(int size) {
        this.size = size;
        this.slots = new ItemStack[size];
    }

    public boolean addItem(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return true;

        // First try to merge with existing stacks
        for (int i = 0; i < size; i++) {
            if (slots[i] != null && slots[i].canMergeWith(stack)) {
                int remaining = slots[i].add(stack.getCount());
                if (remaining == 0) return true;
                stack.setCount(remaining);
            }
        }

        // Then find empty slot
        for (int i = 0; i < size; i++) {
            if (slots[i] == null || slots[i].isEmpty()) {
                slots[i] = stack.copy();
                return true;
            }
        }
        return false; // Full
    }

    public boolean removeItem(ItemType type, int count) {
        int remaining = count;
        for (int i = 0; i < size && remaining > 0; i++) {
            if (slots[i] != null && slots[i].getType() == type) {
                int remove = Math.min(slots[i].getCount(), remaining);
                slots[i].consume(remove);
                remaining -= remove;
                if (slots[i].isEmpty()) slots[i] = null;
            }
        }
        return remaining == 0;
    }

    public int countItem(ItemType type) {
        int count = 0;
        for (ItemStack slot : slots) {
            if (slot != null && slot.getType() == type) count += slot.getCount();
        }
        return count;
    }

    public boolean hasItem(ItemType type, int amount) {
        return countItem(type) >= amount;
    }

    public ItemStack getSlot(int index) {
        if (index < 0 || index >= size) return null;
        return slots[index];
    }

    public void setSlot(int index, ItemStack stack) {
        if (index >= 0 && index < size) slots[index] = stack;
    }

    public ItemStack getHotbarSlot(int index) {
        if (index < 0 || index >= HOTBAR_SIZE) return null;
        return slots[index];
    }

    public ItemStack removeSlot(int index) {
        if (index < 0 || index >= size) return null;
        ItemStack removed = slots[index];
        slots[index] = null;
        return removed;
    }

    public void swapSlots(int a, int b) {
        if (a < 0 || a >= size || b < 0 || b >= size) return;
        ItemStack temp = slots[a];
        slots[a] = slots[b];
        slots[b] = temp;
    }

    public int size() { return size; }
    public int getHotbarSize() { return HOTBAR_SIZE; }
    public ItemStack[] getSlots() { return slots; }
    public boolean isEmpty() {
        for (ItemStack s : slots) if (s != null && !s.isEmpty()) return false;
        return true;
    }
}
