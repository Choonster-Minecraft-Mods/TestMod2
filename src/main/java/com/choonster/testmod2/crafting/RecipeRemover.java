package com.choonster.testmod2.crafting;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.choonster.testmod2.Logger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.common.ChestGenHooks;

/**
 * Copyright (c) 2013 Slime Knights (mDiyo, fuj1n, Sunstrike, progwml6, pillbox) - MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
@SuppressWarnings("unchecked")
public class RecipeRemover
{
	public static void removeShapedRecipes (List<ItemStack> removelist)
	{
		for (ItemStack stack : removelist)
			removeShapedRecipe(stack);
	}

	public static void removeAnyRecipe (ItemStack resultItem)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			ItemStack recipeResult = tmpRecipe.getRecipeOutput();
			if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
			{
				recipes.remove(i--);
			}
		}
	}

	public static void removeAnyRecipe(Item resultItem){
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			ItemStack recipeResult = tmpRecipe.getRecipeOutput();
			if (recipeResult != null && recipeResult.getItem() == resultItem)
			{
				recipes.remove(i--);
			}
		}
	}

	public static void removeShapedRecipe (ItemStack resultItem)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof ShapedRecipes)
			{
				ShapedRecipes recipe = (ShapedRecipes) tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();

				if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
				{
					recipes.remove(i--);
				}
			}
		}
	}

	public static void removeShapelessRecipe (ItemStack resultItem)
	{
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (int i = 0; i < recipes.size(); i++)
		{
			IRecipe tmpRecipe = recipes.get(i);
			if (tmpRecipe instanceof ShapelessRecipes)
			{
				ShapelessRecipes recipe = (ShapelessRecipes) tmpRecipe;
				ItemStack recipeResult = recipe.getRecipeOutput();

				if (ItemStack.areItemStacksEqual(resultItem, recipeResult))
				{
					recipes.remove(i--);
				}
			}
		}
	}

	public static void removeFurnaceRecipe (ItemStack resultItem)
	{
		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.smelting().getSmeltingList();
		for (Iterator<Map.Entry<ItemStack,ItemStack>> entries = recipes.entrySet().iterator(); entries.hasNext(); ){
			Map.Entry<ItemStack,ItemStack> entry = entries.next();
			ItemStack result = entry.getValue();
			if (ItemStack.areItemStacksEqual(result, resultItem)){ // If the output matches the specified ItemStack,
				entries.remove(); // Remove the recipe
			}
		}
	}

	public static void removeFurnaceRecipe (Item i, int metadata)
	{
		removeFurnaceRecipe(new ItemStack(i, 1, metadata));
	}

	public static void removeFurnaceRecipe (Item i)
	{
		removeFurnaceRecipe(new ItemStack(i, 1, 32767));
	}

	//removes from all vanilla worldgen chests :D
	public static void removeFromChests (ItemStack resultItem)
	{
		ChestGenHooks.getInfo(ChestGenHooks.BONUS_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.VILLAGE_BLACKSMITH).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_DESERT_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_CHEST).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.PYRAMID_JUNGLE_DISPENSER).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CORRIDOR).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).removeItem(resultItem);
		ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).removeItem(resultItem);

	}
}