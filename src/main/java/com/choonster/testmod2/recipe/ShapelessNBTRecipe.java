package com.choonster.testmod2.recipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A shapeless ore recipe that supports NBT-based ingredients.
 */
public class ShapelessNBTRecipe extends ShapelessOreRecipe {
	public ShapelessNBTRecipe(Block result, Object... recipe) {
		super(result, recipe);
	}

	public ShapelessNBTRecipe(Item result, Object... recipe) {
		super(result, recipe);
	}

	public ShapelessNBTRecipe(ItemStack result, Object... recipe) {
		super(result, recipe);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean matches(InventoryCrafting var1, World world) {
		ArrayList<Object> required = new ArrayList<>(getInput());

		for (int x = 0; x < var1.getSizeInventory(); x++) {
			ItemStack slot = var1.getStackInSlot(x);

			if (slot != null) {
				boolean inRecipe = false;
				Iterator<Object> req = required.iterator();

				while (req.hasNext()) {
					boolean match = false;

					Object next = req.next();

					if (next instanceof ItemStack) {
						match = itemMatches((ItemStack) next, slot, false);
					} else if (next instanceof ArrayList) {
						Iterator<ItemStack> itr = ((ArrayList<ItemStack>) next).iterator();
						while (itr.hasNext() && !match) {
							match = itemMatches(itr.next(), slot, false);
						}
					}

					if (match) {
						inRecipe = true;
						req.remove();
						break;
					}
				}

				if (!inRecipe) {
					return false;
				}
			}
		}

		return required.isEmpty();
	}

	protected boolean itemMatches(ItemStack target, ItemStack input, boolean strict) {
		return OreDictionary.itemMatches(target, input, strict) && ItemStack.areItemStackTagsEqual(target, input);
	}
}
