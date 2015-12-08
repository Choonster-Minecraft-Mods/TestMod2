package com.choonster.testmod2.creativetab;

import com.choonster.testmod2.init.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class CreativeTabTestMod2 extends CreativeTabs {

	private final ItemSorter itemSorter = new ItemSorter();

	public CreativeTabTestMod2() {
		super("testmod2");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem() {
		return ModItems.candyButton;
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	@Override
	public void displayAllReleventItems(List items) {
		super.displayAllReleventItems(items);

		ItemStack record11 = new ItemStack(Items.record_11);
		record11.addEnchantment(Enchantment.unbreaking, 25);
		items.add(record11);

		// Sort the item list using the ItemSorter instance
		Collections.sort(items, itemSorter);
	}
}
