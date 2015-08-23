package com.choonster.testmod2.item;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

/**
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,33200.0.html
 */
public class ItemRecordRawk extends ItemRecord {
	public ItemRecordRawk() {
		super("rawk");
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("record");
		setTextureName("record_13");
	}

	@Override
	public ResourceLocation getRecordResource(String name) {
		return new ResourceLocation(References.MODID, "record.rawk");
	}
}
