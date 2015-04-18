package com.choonster.testmod2.item;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2408066-try-creating-a-music-disc-in-my-1-8-mod-please
public class ItemRecordSolaris extends ItemRecord {
	public ItemRecordSolaris() {
		super("solaris");
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("record");
		setTextureName("record_13");
	}

	@Override
	public ResourceLocation getRecordResource(String name) {
		return new ResourceLocation(References.MODID, name);
	}
}
