package com.choonster.testmod2.init;

import com.choonster.testmod2.recipe.ShapedEnchantingRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;


public class ModRecipes {

	public static void registerRecipes() {
		registerRecipeClasses();
		addCraftingRecipes();
		addSmeltingRecipes();
		removeRecipes();
	}

	public static void registerRecipeClasses() {
		RecipeSorter.register("testmod2:shapedenchanting", ShapedEnchantingRecipe.class, RecipeSorter.Category.SHAPED, "after:forge:shapedore");
	}

	public static void addCraftingRecipes() {
		// -- Enchanting Recipe --
		GameRegistry.addRecipe(new ShapedEnchantingRecipe(Items.book, 30, " A ", "ABA", " A ", 'A', "ingotGold", 'B', Items.book));

		// -- Staff Upgrade --
		ItemStack staffLevel1 = new ItemStack(ModItems.staff, 1, 0); // Metadata 0 = level 1
		ItemStack staffLevel2 = new ItemStack(ModItems.staff, 1, 1); // Metadata 1 = level 2

		GameRegistry.addRecipe(staffLevel2, "GGG", "GSG", "GGG", 'G', Items.gold_nugget, 'S', staffLevel1); // Level 1 + Gold Nuggets = Level 2

		// -- Container Items --
		GameRegistry.addShapelessRecipe(new ItemStack(Items.fish), new ItemStack(ModItems.containerTest, 1, OreDictionary.WILDCARD_VALUE));

		// The unbreaking container doesn't change metadata values, so just use it directly as an ingredient
		GameRegistry.addShapelessRecipe(new ItemStack(Items.blaze_rod), ModItems.containerUnbreaking);

		// The breaking container does change metadata values, so use an ItemStack with OreDictionary.WILDCARD_VALUE as the metadata value as an ingredient
		GameRegistry.addShapelessRecipe(new ItemStack(Items.blaze_rod), new ItemStack(ModItems.containerBreaking, 1, OreDictionary.WILDCARD_VALUE));

		// -- Miscellaneous Recipes --
		GameRegistry.addRecipe(new ItemStack(Items.stick, 1), "X ", "X ", 'X', Blocks.obsidian);
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.stone_button), "G S", 'G', Items.gold_nugget, 'S', Blocks.stone);

		GameRegistry.addRecipe(new ItemStack(Blocks.diamond_block), "PP", "PP", 'P', Blocks.planks);
		GameRegistry.addRecipe(new ItemStack(Blocks.diamond_block), "SSS", 'S', new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.record_13), new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.tallgrass, 1, 1));
	}

	public static void addSmeltingRecipes() {
		GameRegistry.addSmelting(Blocks.coal_block, new ItemStack(Blocks.obsidian, 1), 20.0f);
	}

	public static void removeRecipes() {

	}
}
