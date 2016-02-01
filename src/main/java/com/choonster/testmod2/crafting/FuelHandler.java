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

	/**
	 * Get the burn time of the specified fuel {@link ItemStack}.
	 * <p>
	 * Despite the return type being {@link int}, the returned value must not be greater than {@link Short#MAX_VALUE} because the furnace saves burn time as a {@link short} in the NBT.
	 *
	 * @param fuel The fuel ItemStack
	 * @return The burn time, or 0 if it's not a fuel.
	 */
	@Override
	public int getBurnTime(ItemStack fuel) {
		Item fuelItem = fuel.getItem();

		if (fuelItem == ModItems.fuel) {
			return 11200;
		} else if (fuelItem == ModItems.fuel2) {
			return 112000; // This won't work properly because it can't fit in a short
		}

		return 0;
	}
}
