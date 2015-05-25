package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

// Uses the container item system to damage the item each time it's crafted with and return a bowl when it's used up.
// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2433786-i-need-a-durable-item-i-created-to-return-an-item
public class ItemContainerTest extends Item {
	public ItemContainerTest() {
		setMaxDamage(3);
		setUnlocalizedName("containertest");
		setTextureName("mushroom_stew");
		setCreativeTab(TestMod2.tab);
	}

	@Override
	public boolean hasContainerItem(ItemStack itemStack) {
		return true;
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
		return false;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		if (itemStack.attemptDamageItem(1, itemRand)) {
			// The item has run out of durability, return a bowl
			return new ItemStack(Items.bowl);
		} else {
			// The stack has been damaged, return it
			return itemStack;
		}
	}
}
