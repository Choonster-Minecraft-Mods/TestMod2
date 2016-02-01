package com.choonster.testmod2.crafting;

import com.choonster.testmod2.init.ModItems;
import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * A fuel handler for this mod's items.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2606540-tutorial-how-to-add-fuel-recipes-1-7-10
 *
 * @author Choonster
 */
public class FuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack fuel) {
		Item fuelItem = fuel.getItem();

		if (fuelItem == ModItems.fuel) {
			return 11200;
		}

		return 0;
	}
}
