package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.util.FoodUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoodAdder extends Item {
	public ItemFoodAdder() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("foodadder");
		setTextureName("minecraft:apple");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			player.getFoodStats().addStats(2, 0.0f);
			FoodUtils.sendHungerMessage(player);
		}

		return stack;
	}
}
