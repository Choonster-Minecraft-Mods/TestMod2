package com.choonster.testmod2.crafting;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.init.ItemRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;


public class CraftingManager {

	public static void mainRegistry() {

		addCraftingRecipe();
		addSmeltingRecipe();
		removeRecipes();
	}

	public static void addCraftingRecipe() {
		// Shaped Recipe
		//GameRegistry.addRecipe(new ItemStack(CustomBlocks.MysteryBlock, 1), new Object[]{"XXX","XYX","XXX",'X', Items.redstone, 'Y', Items.leather});
		//GameRegistry.addRecipe(new ItemStack(CustomItems.obsidianStick, 1), new Object[]{" X "," X ",'X', Blocks.obsidian});
		//GameRegistry.addRecipe(new ItemStack(CustomItems.obsidianStick, 1), new Object[]{" X", " X", 'X', Blocks.obsidian});
		GameRegistry.addRecipe(new ItemStack(Items.stick, 1), new Object[]{"X ", "X ", 'X', Blocks.obsidian});

		//Shapeless Recipe
		//GameRegistry.addShapelessRecipe(new ItemStack(Items.diamond,3), new Object[]{"G","O","X",'G', Items.glowstone_dust, 'O', CustomBlocks.MysteryBlock, 'X', Items.gold_ingot});


		ItemStack staffLevel1 = new ItemStack(ItemRegistry.staff, 1, 0); // Metadata 0 = level 1
		ItemStack staffLevel2 = new ItemStack(ItemRegistry.staff, 1, 1); // Metadata 1 = level 2

		GameRegistry.addRecipe(staffLevel2, "GGG", "GSG", "GGG", 'G', Items.gold_nugget, 'S', staffLevel1); // Level 1 + Gold Nuggets = Level 2


		GameRegistry.addShapedRecipe(new ItemStack(Blocks.stone_button), "G S", 'G', Items.gold_nugget, 'S', Blocks.stone);

	}

	public static void addSmeltingRecipe() {

		GameRegistry.addSmelting(Blocks.coal_block, new ItemStack(Blocks.obsidian, 1), 20.0f);
	}

	public static void removeRecipes(){
		//RecipeRemover.removeAnyRecipe(Item.getItemFromBlock(Blocks.torch));
		//RecipeRemover.removeFurnaceRecipe(new ItemStack(Blocks.stone));
	}
}
