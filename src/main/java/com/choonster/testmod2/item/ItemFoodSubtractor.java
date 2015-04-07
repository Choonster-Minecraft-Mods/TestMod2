package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.util.FoodUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoodSubtractor extends Item {

	public ItemFoodSubtractor() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("foodsubtractor");
		setTextureName("minecraft:rotten_flesh");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.addExhaustion(16.0f);
		if (!world.isRemote) {
			FoodUtils.sendHungerMessage(player);
		}
		return stack;
	}
}
