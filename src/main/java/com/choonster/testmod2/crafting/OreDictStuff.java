package com.choonster.testmod2.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictStuff {
	public static String[] getOreNames(ItemStack itemStack) {
		int[] ids = OreDictionary.getOreIDs(itemStack);
		String[] names = new String[ids.length];

		for (int i = 0; i < ids.length; i++) {
			names[i] = OreDictionary.getOreName(ids[i]);
		}


		return names;
	}
}
