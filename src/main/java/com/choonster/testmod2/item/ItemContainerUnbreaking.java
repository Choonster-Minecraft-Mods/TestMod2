package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.item.Item;

// A container item that doesn't break
// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2447237-how-do-i-make-an-item-in-a-recipe-not-consumed
public class ItemContainerUnbreaking extends Item {
	public ItemContainerUnbreaking() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("containerUnbreaking");
		setTextureName("egg");
		setContainerItem(this);
	}
}
