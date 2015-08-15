package com.choonster.testmod2.compat;

import com.choonster.testmod2.init.ModBlocks;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2446431-blacklisting-buildcraft-facades-in-the-latest
public class BCCompat {
	public static void init() {
		FMLInterModComms.sendMessage("BuildCraft|Transport", "blacklist-facade", new ItemStack(ModBlocks.superTNT));
	}
}
