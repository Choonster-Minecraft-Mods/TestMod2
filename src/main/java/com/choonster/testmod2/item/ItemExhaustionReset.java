package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.util.FoodUtils;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public class ItemExhaustionReset extends Item {
	public ItemExhaustionReset() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("exhaustionresetter");
		setTextureName("minecraft:apple_golden");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote){
			FoodStats stats = player.getFoodStats();
			FoodUtils.setExhaustion(stats, 0);
			FoodUtils.sendHungerMessage(player);
		}

		return stack;
	}
}
