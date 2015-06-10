package com.choonster.testmod2.compat;

import com.choonster.testmod2.init.BlockRegistry;
import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.item.ItemStack;

public class BCCompat {
	public static void init(){
		//FMLInterModComms.sendMessage("BuildCraft|Transport", "blacklist-facade", new ItemStack(BlockRegistry.superTNT));
	}
}
