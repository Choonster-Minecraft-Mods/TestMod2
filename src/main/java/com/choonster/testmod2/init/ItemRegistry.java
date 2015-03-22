package com.choonster.testmod2.init;

import com.choonster.testmod2.item.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemRegistry {
	public static Item coloredBed;
	public static Item collisionTest;
	public static Item fireWand;
	public static Item snowballNoGrav;
	public static Item candyButton;
	public static Item foodAdder;
	public static Item foodSubtractor;
	public static Item foodExhaustionResetter;

	public static void registerItems() {
		coloredBed = registerItem(new ItemColoredBed());
		collisionTest = registerItem(new ItemCollisionTest());
		fireWand = registerItem(new ItemFireWand());
		snowballNoGrav = registerItem(new ItemSnowballNoGrav());
		foodAdder = registerItem(new ItemFoodAdder());
		foodSubtractor = registerItem(new ItemFoodSubtractor());
		foodExhaustionResetter = registerItem(new ItemExhaustionReset());
	}

	private static Item registerItem(Item item){
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		return item;
	}
}
