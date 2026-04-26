package com.minecraft2.android.crafting;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;

public class CraftingRecipe {

    public enum RecipeType { SHAPED, SHAPELESS, SMELTING, GUN_WORKBENCH }

    private final RecipeType recipeType;
    private final ItemType[][] shaped;
    private final ItemType[] shapeless;
    private final ItemStack result;
    private final String category;

    // Shaped recipe constructor
    public CraftingRecipe(ItemType[][] shaped, ItemStack result, String category) {
        this.recipeType = RecipeType.SHAPED;
        this.shaped = shaped;
        this.shapeless = null;
        this.result = result;
        this.category = category;
    }

    // Shapeless recipe constructor
    public CraftingRecipe(ItemType[] shapeless, ItemStack result, String category) {
        this.recipeType = RecipeType.SHAPELESS;
        this.shaped = null;
        this.shapeless = shapeless;
        this.result = result;
        this.category = category;
    }

    // Smelting/special recipe
    public CraftingRecipe(RecipeType type, ItemType input, ItemStack result, String category) {
        this.recipeType = type;
        this.shapeless = new ItemType[]{input};
        this.shaped = null;
        this.result = result;
        this.category = category;
    }

    public RecipeType getRecipeType() { return recipeType; }
    public ItemType[][] getShaped() { return shaped; }
    public ItemType[] getShapeless() { return shapeless; }
    public ItemStack getResult() { return result.copy(); }
    public String getCategory() { return category; }

    public boolean matches(ItemType[][] grid) {
        if (recipeType != RecipeType.SHAPED) return false;
        if (grid.length != shaped.length) return false;
        for (int r = 0; r < shaped.length; r++) {
            if (grid[r].length != shaped[r].length) return false;
            for (int c = 0; c < shaped[r].length; c++) {
                if (shaped[r][c] != grid[r][c]) return false;
            }
        }
        return true;
    }

    public boolean matchesShapeless(ItemType[] available) {
        if (recipeType != RecipeType.SHAPELESS) return false;
        int[] needed = new int[shapeless.length];
        for (int i = 0; i < needed.length; i++) needed[i] = 1;

        for (ItemType item : available) {
            for (int i = 0; i < shapeless.length; i++) {
                if (item == shapeless[i] && needed[i] > 0) {
                    needed[i]--;
                    break;
                }
            }
        }
        for (int n : needed) if (n > 0) return false;
        return true;
    }
}
