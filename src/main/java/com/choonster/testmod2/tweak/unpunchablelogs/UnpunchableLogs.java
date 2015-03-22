package com.choonster.testmod2.tweak.unpunchablelogs;

import com.choonster.testmod2.Logger;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class UnpunchableLogs {

	public static MaterialUnpunchableLogs materialUnpunchableLogs = new MaterialUnpunchableLogs(); // Instantiate the custom Material

	public static void init() {
		ArrayList<ItemStack> logItemStacks = OreDictionary.getOres("logWood"); // Get a list of ItemStacks registered for "logWood"
		for (ItemStack logItemStack : logItemStacks) { // For each ItemStack,
			Block logBlock = Block.getBlockFromItem(logItemStack.getItem()); // Get the ItemStack's Block
			if (logBlock != null && logBlock.getMaterial() != materialUnpunchableLogs) { // If the Block exists and doesn't have the custom Material
				ObfuscationReflectionHelper.setPrivateValue(Block.class, logBlock, materialUnpunchableLogs, "blockMaterial", "field_149764_J"); // Set the Block's Material to the custom Material
				Logger.info("Set material to UnpunchableLogs! %s %s", logBlock, logBlock.getUnlocalizedName());
			}
		}
	}
}
