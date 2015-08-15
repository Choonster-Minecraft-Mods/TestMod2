package com.choonster.testmod2.recipe;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Random;

/**
 * A shaped ore recipe that applies a random enchantment of the specified level to the output.
 * <p>
 * Note: To output an Enchanted Book, use Items.book instead of Items.enchanted_book as the result, otherwise
 * EnchantmentHelper won't apply the enchantments properly.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2497706-random-crafting
 */
public class ShapedEnchantingRecipe extends ShapedOreRecipe {
	private final Random random = new Random();
	private final int enchantmentLevel;

	public ShapedEnchantingRecipe(Block result, int enchantmentLevel, Object... recipe) {
		super(result, recipe);
		this.enchantmentLevel = enchantmentLevel;
	}

	public ShapedEnchantingRecipe(Item result, int enchantmentLevel, Object... recipe) {
		super(result, recipe);
		this.enchantmentLevel = enchantmentLevel;
	}

	public ShapedEnchantingRecipe(ItemStack result, int enchantmentLevel, Object... recipe) {
		super(result, recipe);
		this.enchantmentLevel = enchantmentLevel;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
		ItemStack result = super.getCraftingResult(inventoryCrafting);
		EnchantmentHelper.addRandomEnchantment(random, result, enchantmentLevel);
		return result;
	}
}
