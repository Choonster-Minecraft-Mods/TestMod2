package com.choonster.testmod2.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class OreDictStuff {

	/**
	 * Gets an array of ore names for the specified ore.
	 *
	 * @param itemStack The ore
	 * @return An array of ore names
	 */
	public static String[] getOreNames(ItemStack itemStack) {
		int[] ids = OreDictionary.getOreIDs(itemStack);
		String[] names = new String[ids.length];

		for (int i = 0; i < ids.length; i++) {
			names[i] = OreDictionary.getOreName(ids[i]);
		}

		return names;
	}

	/**
	 * Gets the ore name corresponding to the specified list of ores.
	 *
	 * @param ores The list of ores
	 * @return The corresponding ore name
	 * @throws InvalidOreException When no matching ore name is found
	 */
	public static String getOreNameForOreList(List<ItemStack> ores) throws InvalidOreException {
		// OreDictionary.getOres returns an empty list if no ores have been registered for the specified name
		if (ores.isEmpty()) {
			throw new InvalidOreException("Ore list is empty");
		} else {
			for (String possibleOreName : getOreNames(ores.get(0))) {
				if (ores.equals(OreDictionary.getOres(possibleOreName))) {
					return possibleOreName;
				}
			}
		}

		throw new InvalidOreException("Unable to find name for ores " + ArrayUtils.toString(ores.toArray()));
	}

	public static class InvalidOreException extends Exception {
		public InvalidOreException() {
		}

		public InvalidOreException(String message) {
			super(message);
		}

		public InvalidOreException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
