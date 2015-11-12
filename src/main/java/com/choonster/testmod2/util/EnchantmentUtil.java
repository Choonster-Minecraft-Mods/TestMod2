package com.choonster.testmod2.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class EnchantmentUtil {

	/**
	 * Remove the specified {@link Enchantment} from the {@link ItemStack}
	 *
	 * @param enchantment The Enchantment
	 * @param itemStack   The ItemStack
	 */
	public static void removeEnchantment(Enchantment enchantment, ItemStack itemStack) {
		NBTTagList enchantmentTagList = itemStack.getEnchantmentTagList();
		if (enchantmentTagList != null) { // If the ItemStack has an enchantment tag list
			for (int i = 0; i < enchantmentTagList.tagCount(); i++) { // Iterate through the list
				NBTTagCompound enchantmentTag = enchantmentTagList.getCompoundTagAt(i); // Get the individual enchantment tag
				if (enchantmentTag.getShort("id") == enchantment.effectId) { // If the enchantment tag is the Enchantment to remove,
					enchantmentTagList.removeTag(i); // Remove it
					break;
				}
			}

			if (enchantmentTagList.tagCount() == 0) { // If there are no more enchantments on the ItemStack,
				itemStack.getTagCompound().removeTag("ench"); // Remove the enchantment tag list
			}
		}
	}
}
