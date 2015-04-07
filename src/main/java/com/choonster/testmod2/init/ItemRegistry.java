package com.choonster.testmod2.init;

import com.choonster.testmod2.item.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ItemRegistry {
	public static ItemColoredBed coloredBed;
	public static ItemCollisionTest collisionTest;
	public static ItemFireWand fireWand;
	public static ItemSnowballNoGrav snowballNoGrav;
	public static Item candyButton;
	public static ItemFoodAdder foodAdder;
	public static ItemFoodSubtractor foodSubtractor;
	public static ItemExhaustionReset foodExhaustionResetter;
	public static ItemStaff staff;
	public static ItemChiselAndHammer chiselAndHammer;

	public static void registerItems() {
		coloredBed = registerItem(new ItemColoredBed());
		collisionTest = registerItem(new ItemCollisionTest());
		fireWand = registerItem(new ItemFireWand());
		snowballNoGrav = registerItem(new ItemSnowballNoGrav());
		foodAdder = registerItem(new ItemFoodAdder());
		foodSubtractor = registerItem(new ItemFoodSubtractor());
		foodExhaustionResetter = registerItem(new ItemExhaustionReset());
		staff = registerItem(new ItemStaff());
		chiselAndHammer = registerItem(new ItemChiselAndHammer());
	}

	private static <T extends Item> T registerItem(T item) {
		GameRegistry.registerItem(item, item.getUnlocalizedName().substring(5));
		return item;
	}
}
