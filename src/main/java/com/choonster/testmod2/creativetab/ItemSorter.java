package com.choonster.testmod2.creativetab;

import net.minecraft.item.Item;
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

		if (priority1 == priority2) { // Both stacks have the same priority, order them by their ID/metadata
			Item item1 = stack1.getItem();
			Item item2 = stack2.getItem();

			if (item1 == item2) { // If the stacks have the same item, order them by their metadata
				return stack1.getMetadata() - stack2.getMetadata();
			} else { // Else order them by their ID
				return Item.getIdFromItem(item1) - Item.getIdFromItem(item2);
			}
		} else { // Stacks have different priorities, order them by priority instead
			return priority1.compareTo(priority2);
		}
	}
}
