package com.choonster.testmod2.init;

import com.choonster.testmod2.References;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class ModLoot {
	public static final ChestGenHooks RIGHT_CLICK_LOOT_BLOCK = ChestGenHooks.getInfo(References.MODID + ":rightClickLoot");

	public static void initLoot() {
		RIGHT_CLICK_LOOT_BLOCK.setMin(5);
		RIGHT_CLICK_LOOT_BLOCK.setMax(20);

		// new WeightedRandomChestContent(item, minAmount, maxAmount, weight)

		RIGHT_CLICK_LOOT_BLOCK.addItem(new WeightedRandomChestContent(new ItemStack(Items.carrot_on_a_stick), 1, 5, 10));
		RIGHT_CLICK_LOOT_BLOCK.addItem(new WeightedRandomChestContent(new ItemStack(Items.repeater), 5, 60, 15));
		RIGHT_CLICK_LOOT_BLOCK.addItem(new WeightedRandomChestContent(new ItemStack(ModItems.cannedPeaches), 1, 64, 20));
	}
}
