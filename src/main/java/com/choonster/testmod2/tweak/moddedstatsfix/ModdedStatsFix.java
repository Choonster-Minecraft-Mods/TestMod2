package com.choonster.testmod2.tweak.moddedstatsfix;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.config.Config;
import com.choonster.testmod2.util.ArrayUtils;
import com.choonster.testmod2.util.ChatComponentDeferred;
import com.choonster.testmod2.util.IOUtils;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLModIdMappingEvent;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
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

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Adds the missing stats for modded blocks and items.
 * <p>
 * Adapted from StatList, but only initialises stats that haven't already been initialised and doesn't merge stats for
 * dual blocks (because vanilla already does that).
 * <p>
 * Uses {@link ChatComponentDeferred} for the stat names because some mods (e.g. ChromatiCraft) don't expect
 * {@link ItemStack#func_151000_E()} (getChatComponent) to be called before the world has been loaded. This ensures that
 * the method is only called when the stat name needs to be displayed somewhere.
 */
public class ModdedStatsFix {

	private final FMLControlledNamespacedRegistry<Block> blockRegistry;
	private final FMLControlledNamespacedRegistry<Item> itemRegistry;

	// These fields are type-safe references to the corresponding vanilla fields.
	private final List<StatBase> objectMineStats;
	private final List<StatBase> itemStats;

	private final StatBase[] objectCraftStats;
	private final StatBase[] mineBlockStatArray;
	private final StatBase[] objectUseStats;
	private final StatBase[] objectBreakStats;

	private final Map<Item, StatBase> modCraftStats = new HashMap<>();
	private final Map<Block, StatBase> modMineStats = new HashMap<>();
	private final Map<Item, StatBase> modUseStats = new HashMap<>();
	private final Map<Item, StatBase> modBreakStats = new HashMap<>();

	private static ModdedStatsFix INSTANCE;

	@SuppressWarnings("unchecked")
	private ModdedStatsFix() {
		blockRegistry = GameData.getBlockRegistry();
		itemRegistry = GameData.getItemRegistry();

		objectCraftStats = StatList.objectCraftStats;
		mineBlockStatArray = StatList.mineBlockStatArray;
		objectMineStats = StatList.objectMineStats;
		itemStats = StatList.itemStats;
		objectUseStats = StatList.objectUseStats;
		objectBreakStats = StatList.objectBreakStats;
	}

	public static void addStats() {
		if (INSTANCE == null) {
			INSTANCE = new ModdedStatsFix();
			INSTANCE.initMiningStats();
			INSTANCE.initUseStats();
			INSTANCE.initBreakStats();
			INSTANCE.initCraftableStats();
		}
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

		craftableItems.stream().filter(item -> item != null).forEach(item -> {
			int id = itemRegistry.getId(item);

			if (objectCraftStats[id] == null) {
				StatBase stat = new StatCrafting("stat.craftItem." + itemRegistry.getNameForObject(item), ChatComponentDeferred.create(() -> new ChatComponentTranslation("stat.craftItem", new ItemStack(item).func_151000_E())), item).registerStat();
				objectCraftStats[id] = stat;
				modCraftStats.put(item, stat);
			}
		});
	}

	/**
	 * Initialises statistics related to mining blocks.
	 */
	private void initMiningStats() {
		for (Block block : blockRegistry.typeSafeIterable()) {
			if (Item.getItemFromBlock(block) != null) {
				int id = blockRegistry.getId(block);

				if (block.getEnableStats() && mineBlockStatArray[id] == null) {
					StatBase stat = new StatCrafting("stat.mineBlock." + blockRegistry.getNameForObject(block), ChatComponentDeferred.create(() -> new ChatComponentTranslation("stat.mineBlock", new ItemStack(block).func_151000_E())), Item.getItemFromBlock(block)).registerStat();
					mineBlockStatArray[id] = stat;
					objectMineStats.add(stat);
					modMineStats.put(block, stat);
				}
			}
		}
	}

	/**
	 * Initialises statistics related to using items.
	 */
	private void initUseStats() {
		for (Item item : itemRegistry.typeSafeIterable()) {
			if (item != null) {
				int id = itemRegistry.getId(item);

				if (objectUseStats[id] == null) {
					StatBase stat = new StatCrafting("stat.useItem." + itemRegistry.getNameForObject(item), ChatComponentDeferred.create(() -> new ChatComponentTranslation("stat.useItem", new ItemStack(item).func_151000_E())), item).registerStat();
					objectUseStats[id] = stat;
					modUseStats.put(item, stat);

					if (!(item instanceof ItemBlock)) {
						itemStats.add(stat);
					}
				}
			}
		}
	}

	/**
	 * Initialises statistics related to breaking items.
	 */
	private void initBreakStats() {
		for (Item item : itemRegistry.typeSafeIterable()) {
			if (item != null) {
				int id = itemRegistry.getId(item);

				if (item.isDamageable() && objectBreakStats[id] == null) {
					StatBase stat = new StatCrafting("stat.breakItem." + itemRegistry.getNameForObject(item), ChatComponentDeferred.create(() -> new ChatComponentTranslation("stat.breakItem", new ItemStack(item).func_151000_E())), item).registerStat();
					objectBreakStats[id] = stat;
					modBreakStats.put(item, stat);
				}
			}
		}
	}

	/**
	 * Remap mod stats to use the new block/item IDs
	 *
	 * @param remappings The IDs that have been remapped
	 */
	public static void remapIDs(List<FMLModIdMappingEvent.ModRemapping> remappings) {
		INSTANCE.remap(remappings);
	}

	/**
	 * Remap mod stats to use the new block/item IDs
	 *
	 * @param remappings The IDs that have been remapped
	 */
	private void remap(List<FMLModIdMappingEvent.ModRemapping> remappings) {
		Logger.info("Remapping stats for %d blocks/items", remappings.size());

		remapStatArray(objectCraftStats, modCraftStats, itemRegistry, "craft");
		remapStatArray(mineBlockStatArray, modMineStats, blockRegistry, "mine");
		remapStatArray(objectUseStats, modUseStats, itemRegistry, "use");
		remapStatArray(objectBreakStats, modBreakStats, itemRegistry, "break");
	}

	/**
	 * Remap the stats in a stat array to use the newly loaded world's block/item IDs.
	 *
	 * @param <T>        Item or Block, depending on the stat type
	 * @param statArray  The stat array
	 * @param modStatMap The corresponding map containing the mod stats of this type for each item/block
	 * @param registry   The block/item registry
	 * @param statType   The type of stat, used in the log file name
	 */
	private <T> void remapStatArray(StatBase[] statArray, Map<T, StatBase> modStatMap, FMLControlledNamespacedRegistry<T> registry, String statType) {
		Path logPath = Paths.get("logs", String.format("TestMod2StatRemapping_%s_%s.log", FMLCommonHandler.instance().getSide().name().toLowerCase(Locale.ENGLISH), statType));

		if (Config.writeStatRemappingLogs) {
			Logger.debug("Writing log to %s", logPath.toAbsolutePath());
		}

		try (PrintWriter writer = new PrintWriter(IOUtils.getOptionalWriter(Config.writeStatRemappingLogs, logPath, StandardOpenOption.APPEND, StandardOpenOption.CREATE))) {
			writer.printf("=================\n%s\n=================\n", DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now()));
			writer.printf("%-55s    %-5s    Prev. ID\n", "Stat", "ID");

			Map<StatBase, Integer> removed = ArrayUtils.removeAll(statArray, modStatMap.values()); // Remove all mod stats from the array

			for (Map.Entry<T, StatBase> entry : modStatMap.entrySet()) { // Add all mod stats to the array with the new block/item IDs
				int id = registry.getId(entry.getKey());
				StatBase stat = entry.getValue();
				statArray[id] = stat;

				int previousID = removed.get(stat);
				writer.printf("%-55s    %05d    %05d\n", stat.statId, id, previousID);
			}

			writer.println("\n");
		} catch (IOException e) {
			Logger.error(e, "Error opening stat remap log");
		}
	}
}
