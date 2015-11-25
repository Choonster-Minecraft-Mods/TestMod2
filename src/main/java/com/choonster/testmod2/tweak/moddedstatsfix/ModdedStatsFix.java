package com.choonster.testmod2.tweak.moddedstatsfix;

import cpw.mods.fml.common.functions.GenericIterableFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;

import java.util.HashSet;
import java.util.List;

/**
 * Adds the missing stats for modded blocks and items.
 * <p>
 * Adapted from StatList, but only initialises stats that haven't already been initialised and doesn't merge stats for
 * dual blocks (because vanilla already does that).
 */
public class ModdedStatsFix {

	// These fields are typesafe references to the corresponding vanilla fields.

	private final Iterable<Block> blockRegistry;
	private final Iterable<Item> itemRegistry;

	private final StatBase[] objectCraftStats;
	private final StatBase[] mineBlockStatArray;
	private final List<StatBase> objectMineStats;
	private final List<StatBase> itemStats;
	private final StatBase[] objectUseStats;
	private final StatBase[] objectBreakStats;

	@SuppressWarnings("unchecked")
	private ModdedStatsFix() {
		blockRegistry = GenericIterableFactory.newCastingIterable(Block.blockRegistry, Block.class);
		itemRegistry = GenericIterableFactory.newCastingIterable(Item.itemRegistry, Item.class);

		objectCraftStats = StatList.objectCraftStats;
		mineBlockStatArray = StatList.mineBlockStatArray;
		objectMineStats = StatList.objectMineStats;
		itemStats = StatList.itemStats;
		objectUseStats = StatList.objectUseStats;
		objectBreakStats = StatList.objectBreakStats;
	}

	public static void addStats() {
		ModdedStatsFix instance = new ModdedStatsFix();

		instance.initMiningStats();
		instance.initUseStats();
		instance.initBreakStats();
		instance.initCraftableStats();
	}

	/**
	 * Initialises statistics related to craftable items.
	 */
	private void initCraftableStats() {
		HashSet<Item> craftableItems = new HashSet<>();

		for (Object recipeObject : CraftingManager.getInstance().getRecipeList()) {
			IRecipe recipe = (IRecipe) recipeObject;
			if (recipe.getRecipeOutput() != null) {
				craftableItems.add(recipe.getRecipeOutput().getItem());
			}
		}

		for (Object resultObject : FurnaceRecipes.instance().getSmeltingList().values()) {
			ItemStack resultItemStack = (ItemStack) resultObject;
			craftableItems.add(resultItemStack.getItem());
		}

		for (Item item : craftableItems) {
			if (item != null) {
				int id = Item.getIdFromItem(item);

				if (objectCraftStats[id] == null) {
					objectCraftStats[id] = new StatCrafting("stat.craftItem." + id, new ChatComponentTranslation("stat.craftItem", new ItemStack(item).func_151000_E()), item).registerStat();
				}
			}
		}
	}

	/**
	 * Initialises statistics related to mining blocks.
	 */
	private void initMiningStats() {
		for (Block block : blockRegistry) {
			if (Item.getItemFromBlock(block) != null) {
				int id = Block.getIdFromBlock(block);

				if (block.getEnableStats() && mineBlockStatArray[id] == null) {
					mineBlockStatArray[id] = new StatCrafting("stat.mineBlock." + id, new ChatComponentTranslation("stat.mineBlock", new ItemStack(block).func_151000_E()), Item.getItemFromBlock(block)).registerStat();
					objectMineStats.add(mineBlockStatArray[id]);
				}
			}
		}
	}

	/**
	 * Initialises statistics related to using items.
	 */
	private void initUseStats() {
		for (Item item : itemRegistry) {
			if (item != null) {
				int id = Item.getIdFromItem(item);

				if (objectUseStats[id] == null) {
					objectUseStats[id] = new StatCrafting("stat.useItem." + id, new ChatComponentTranslation("stat.useItem", new ItemStack(item).func_151000_E()), item).registerStat();

					if (!(item instanceof ItemBlock)) {
						itemStats.add(objectUseStats[id]);
					}
				}
			}
		}
	}

	/**
	 * Initialises statistics related to breaking items.
	 */
	private void initBreakStats() {
		for (Item item : itemRegistry) {
			if (item != null) {
				int id = Item.getIdFromItem(item);

				if (item.isDamageable() && objectBreakStats[id] == null) {
					objectBreakStats[id] = new StatCrafting("stat.breakItem." + id, new ChatComponentTranslation("stat.breakItem", new ItemStack(item).func_151000_E()), item).registerStat();
				}
			}
		}
	}
}
