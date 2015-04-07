package com.choonster.testmod2.util;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FoodStats;

public class FoodUtils {
	public static void sendHungerMessage(EntityPlayer player) {
		FoodStats stats = player.getFoodStats();
		NBTTagCompound tagCompound = new NBTTagCompound();
		stats.writeNBT(tagCompound);
		player.addChatMessage(new ChatComponentText(String.format("Level: %d, Saturation: %f, Exhaustion: %f", stats.getFoodLevel(), stats.getSaturationLevel(), getExhaustion(stats))));
	}

	public static float getExhaustion(FoodStats stats) {
		return ObfuscationReflectionHelper.getPrivateValue(FoodStats.class, stats, "foodExhaustionLevel");
	}

	public static void setExhaustion(FoodStats stats, float value) {
		ObfuscationReflectionHelper.setPrivateValue(FoodStats.class, stats, value, "foodExhaustionLevel");
	}
}
