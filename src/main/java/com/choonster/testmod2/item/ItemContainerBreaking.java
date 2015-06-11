package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// A container item that loses durability with each craft
// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2447237-how-do-i-make-an-item-in-a-recipe-not-consumed
public class ItemContainerBreaking extends Item {
	public ItemContainerBreaking() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("containerBreaking");
		setTextureName("cookie");
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if (itemStack.attemptDamageItem(1, itemRand)) { // The item has run out of durability, return null
			return null;
		}

		return itemStack;
	}
}
