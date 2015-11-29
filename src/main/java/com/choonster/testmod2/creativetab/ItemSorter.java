package com.choonster.testmod2.creativetab;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ItemSorter implements Comparator<ItemStack> {
	private final Map<ItemStack, OreDictionaryPriority> oreDictPriorityCache = new HashMap<>();

	private OreDictionaryPriority getOreDictionaryPriority(ItemStack itemStack) {
		OreDictionaryPriority priority = oreDictPriorityCache.get(itemStack);
		if (priority != null) {
			return priority;
		}

		priority = OreDictionaryPriority.NONE;

		for (int oreID : OreDictionary.getOreIDs(itemStack)) {
			String oreName = OreDictionary.getOreName(oreID);

			if (oreName.startsWith("ore")) {
				priority = OreDictionaryPriority.ORE;
				break;
			} else if (oreName.startsWith("ingot")) {
				priority = OreDictionaryPriority.INGOT;
				break;
			} else if (oreName.startsWith("dust")) {
				priority = OreDictionaryPriority.DUST;
				break;
			}
		}

		oreDictPriorityCache.put(itemStack, priority);

		return priority;
	}

	@Override
	public int compare(ItemStack stack1, ItemStack stack2) {
		OreDictionaryPriority priority1 = getOreDictionaryPriority(stack1), priority2 = getOreDictionaryPriority(stack2);

		if (priority1 == priority2) { // Both stacks have the same priority, order them by their display names
			String displayName1 = stack1.getDisplayName();
			String displayName2 = stack2.getDisplayName();

			return displayName1.compareToIgnoreCase(displayName2);
		} else { // Stacks have different priorities, order them by priority instead
			return priority1.compareTo(priority2);
		}
	}
}
