package com.minecraft2.android.crafting;

import com.minecraft2.android.item.ItemStack;
import com.minecraft2.android.item.ItemType;
import java.util.ArrayList;
import java.util.List;

public class RecipeRegistry {

    private static final List<CraftingRecipe> recipes = new ArrayList<>();

    static {
        registerMC1Recipes();
        registerNewMC2Recipes();
    }

    private static void registerMC1Recipes() {
        // Wooden Planks from logs
        recipes.add(new CraftingRecipe(
                new ItemType[]{ItemType.OAK_LOG},
                new ItemStack(ItemType.OAK_PLANKS, 4), "wood"));

        // Crafting Table
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.OAK_PLANKS, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, ItemType.OAK_PLANKS}},
                new ItemStack(ItemType.CRAFTING_TABLE, 1), "wood"));

        // Wooden Pickaxe
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS},
                                 {null, ItemType.STONE, null},
                                 {null, ItemType.STONE, null}},
                new ItemStack(ItemType.WOOD_PICKAXE, 1), "tools"));

        // Stone Pickaxe
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.STONE, ItemType.STONE, ItemType.STONE},
                                 {null, ItemType.STONE, null},
                                 {null, ItemType.STONE, null}},
                new ItemStack(ItemType.STONE_PICKAXE, 1), "tools"));

        // Iron Pickaxe
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.IRON_INGOT, ItemType.IRON_INGOT, ItemType.IRON_INGOT},
                                 {null, ItemType.STONE, null},
                                 {null, ItemType.STONE, null}},
                new ItemStack(ItemType.IRON_PICKAXE, 1), "tools"));

        // Diamond Pickaxe
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.DIAMOND, ItemType.DIAMOND, ItemType.DIAMOND},
                                 {null, ItemType.STONE, null},
                                 {null, ItemType.STONE, null}},
                new ItemStack(ItemType.DIAMOND_PICKAXE, 1), "tools"));

        // Wooden Sword
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS},
                                 {ItemType.STONE}},
                new ItemStack(ItemType.WOOD_SWORD, 1), "weapons"));

        // Iron Sword
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.IRON_INGOT},
                                 {ItemType.IRON_INGOT},
                                 {ItemType.STONE}},
                new ItemStack(ItemType.IRON_SWORD, 1), "weapons"));

        // Diamond Sword
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.DIAMOND},
                                 {ItemType.DIAMOND},
                                 {ItemType.STONE}},
                new ItemStack(ItemType.DIAMOND_SWORD, 1), "weapons"));

        // Bow
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{null, ItemType.STONE, ItemType.STRING},
                                 {ItemType.STONE, null, ItemType.STRING},
                                 {null, ItemType.STONE, ItemType.STRING}},
                new ItemStack(ItemType.BOW, 1), "weapons"));

        // Arrow (batch)
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.FLINT_AND_STEEL},
                                 {ItemType.STONE},
                                 {ItemType.FEATHER}},
                new ItemStack(ItemType.ARROW, 4), "weapons"));

        // Iron Armor
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.IRON_INGOT, null, ItemType.IRON_INGOT},
                                 {ItemType.IRON_INGOT, null, ItemType.IRON_INGOT},
                                 {null, null, null}},
                new ItemStack(ItemType.IRON_HELMET, 1), "armor"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.IRON_INGOT, null, ItemType.IRON_INGOT},
                                 {ItemType.IRON_INGOT, ItemType.IRON_INGOT, ItemType.IRON_INGOT},
                                 {ItemType.IRON_INGOT, ItemType.IRON_INGOT, ItemType.IRON_INGOT}},
                new ItemStack(ItemType.IRON_CHESTPLATE, 1), "armor"));

        // Torch
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.COAL},
                                 {ItemType.STONE}},
                new ItemStack(ItemType.TORCH, 4), "misc"));

        // Chest
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, null, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS}},
                new ItemStack(ItemType.CHEST, 1), "building"));

        // Furnace
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.STONE, ItemType.STONE, ItemType.STONE},
                                 {ItemType.STONE, null, ItemType.STONE},
                                 {ItemType.STONE, ItemType.STONE, ItemType.STONE}},
                new ItemStack(ItemType.FURNACE, 1), "building"));

        // Smelting
        recipes.add(new CraftingRecipe(CraftingRecipe.RecipeType.SMELTING, ItemType.BEEF,
                new ItemStack(ItemType.COOKED_BEEF, 1), "food"));
        recipes.add(new CraftingRecipe(CraftingRecipe.RecipeType.SMELTING, ItemType.PORK,
                new ItemStack(ItemType.COOKED_PORK, 1), "food"));
        recipes.add(new CraftingRecipe(CraftingRecipe.RecipeType.SMELTING, ItemType.CHICKEN_MEAT,
                new ItemStack(ItemType.COOKED_CHICKEN, 1), "food"));
        recipes.add(new CraftingRecipe(CraftingRecipe.RecipeType.SMELTING, ItemType.COD,
                new ItemStack(ItemType.COOKED_COD, 1), "food"));
    }

    private static void registerNewMC2Recipes() {
        // New ore tools
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.TITANIUM_INGOT, ItemType.TITANIUM_INGOT, ItemType.TITANIUM_INGOT},
                                 {null, ItemType.STONE, null},
                                 {null, ItemType.STONE, null}},
                new ItemStack(ItemType.TITANIUM_PICKAXE, 1), "tools"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.ADAMANTITE_INGOT, ItemType.ADAMANTITE_INGOT, ItemType.ADAMANTITE_INGOT},
                                 {null, ItemType.STONE, null},
                                 {null, ItemType.STONE, null}},
                new ItemStack(ItemType.ADAMANTITE_PICKAXE, 1), "tools"));

        // New swords
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.TITANIUM_INGOT},
                                 {ItemType.TITANIUM_INGOT},
                                 {ItemType.STONE}},
                new ItemStack(ItemType.TITANIUM_SWORD, 1), "weapons"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.ADAMANTITE_INGOT},
                                 {ItemType.ADAMANTITE_INGOT},
                                 {ItemType.STONE}},
                new ItemStack(ItemType.ADAMANTITE_SWORD, 1), "weapons"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.CRYSTAL_SHARD},
                                 {ItemType.CRYSTAL_SHARD},
                                 {ItemType.TITANIUM_INGOT}},
                new ItemStack(ItemType.CRYSTAL_BLADE, 1), "weapons"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.VOID_ESSENCE},
                                 {ItemType.ADAMANTITE_INGOT},
                                 {ItemType.TUNGSTEN_INGOT}},
                new ItemStack(ItemType.VOID_BLADE, 1), "weapons"));

        // Armor sets
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.TITANIUM_INGOT, ItemType.TITANIUM_INGOT, ItemType.TITANIUM_INGOT},
                                 {ItemType.TITANIUM_INGOT, null, ItemType.TITANIUM_INGOT},
                                 {ItemType.TITANIUM_INGOT, ItemType.TITANIUM_INGOT, ItemType.TITANIUM_INGOT}},
                new ItemStack(ItemType.TITANIUM_ARMOR_SET, 1), "armor"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.CRYSTAL_SHARD, ItemType.CRYSTAL_SHARD, ItemType.CRYSTAL_SHARD},
                                 {ItemType.CRYSTAL_SHARD, null, ItemType.CRYSTAL_SHARD},
                                 {ItemType.CRYSTAL_SHARD, ItemType.CRYSTAL_SHARD, ItemType.CRYSTAL_SHARD}},
                new ItemStack(ItemType.CRYSTAL_ARMOR_SET, 1), "armor"));

        // Gun Workbench
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.IRON_INGOT, ItemType.IRON_INGOT, ItemType.IRON_INGOT},
                                 {ItemType.OAK_PLANKS, ItemType.IRON_INGOT, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS}},
                new ItemStack(ItemType.CHAIR, 1), "building")); // Placeholder - gun workbench

        // Guns (require Gun Workbench)
        recipes.add(new CraftingRecipe(CraftingRecipe.RecipeType.GUN_WORKBENCH,
                ItemType.IRON_INGOT,
                new ItemStack(ItemType.PISTOL, 1), "guns"));

        recipes.add(new CraftingRecipe(CraftingRecipe.RecipeType.GUN_WORKBENCH,
                ItemType.IRON_INGOT,
                new ItemStack(ItemType.SHOTGUN, 1), "guns"));

        // Furniture
        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.OAK_PLANKS, null, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, null, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS}},
                new ItemStack(ItemType.CHAIR, 1), "furniture"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS}},
                new ItemStack(ItemType.TABLE, 1), "furniture"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.WOOL, ItemType.WOOL, ItemType.WOOL},
                                 {ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS}},
                new ItemStack(ItemType.BED, 1), "furniture"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{null, ItemType.IRON_INGOT, null},
                                 {ItemType.IRON_INGOT, ItemType.GLASS, ItemType.IRON_INGOT},
                                 {null, ItemType.IRON_INGOT, null}},
                new ItemStack(ItemType.LAMP, 1), "furniture"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, null, ItemType.OAK_PLANKS},
                                 {ItemType.OAK_PLANKS, ItemType.OAK_PLANKS, ItemType.OAK_PLANKS}},
                new ItemStack(ItemType.WARDROBE, 1), "furniture"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.GLASS, ItemType.GLASS, ItemType.GLASS},
                                 {ItemType.IRON_INGOT, null, ItemType.IRON_INGOT}},
                new ItemStack(ItemType.SINK, 1), "furniture"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.IRON_INGOT, ItemType.IRON_INGOT, ItemType.IRON_INGOT},
                                 {ItemType.IRON_INGOT, ItemType.REDSTONE, ItemType.IRON_INGOT},
                                 {null, ItemType.IRON_INGOT, null}},
                new ItemStack(ItemType.STOVE, 1), "furniture"));

        recipes.add(new CraftingRecipe(
                new ItemType[][]{{ItemType.IRON_INGOT, ItemType.IRON_INGOT, ItemType.IRON_INGOT},
                                 {ItemType.IRON_INGOT, null, ItemType.IRON_INGOT},
                                 {ItemType.IRON_INGOT, ItemType.IRON_INGOT, ItemType.IRON_INGOT}},
                new ItemStack(ItemType.FRIDGE, 1), "furniture"));

        // Ammo
        recipes.add(new CraftingRecipe(
                new ItemType[]{ItemType.IRON_INGOT, ItemType.GUNPOWDER},
                new ItemStack(ItemType.PISTOL_AMMO, 30), "ammo"));

        recipes.add(new CraftingRecipe(
                new ItemType[]{ItemType.IRON_INGOT, ItemType.GUNPOWDER, ItemType.IRON_INGOT},
                new ItemStack(ItemType.RIFLE_AMMO, 20), "ammo"));

        recipes.add(new CraftingRecipe(
                new ItemType[]{ItemType.IRON_INGOT, ItemType.GUNPOWDER, ItemType.IRON_INGOT, ItemType.IRON_INGOT},
                new ItemStack(ItemType.SHOTGUN_SHELLS, 10), "ammo"));

        recipes.add(new CraftingRecipe(
                new ItemType[]{ItemType.GUNPOWDER, ItemType.IRON_INGOT, ItemType.GUNPOWDER},
                new ItemStack(ItemType.ROCKET, 2), "ammo"));
    }

    public static List<CraftingRecipe> getAllRecipes() { return recipes; }

    public static List<CraftingRecipe> getByCategory(String category) {
        List<CraftingRecipe> filtered = new ArrayList<>();
        for (CraftingRecipe r : recipes) {
            if (r.getCategory().equals(category)) filtered.add(r);
        }
        return filtered;
    }
}
