package com.choonster.testmod2.tweak.ediblesugar;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.crafting.RecipeReplacer;
import com.choonster.testmod2.init.ModItems;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Recipe tweaks for Edible Sugar.
 * <p>
 * Test for these threads:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2468534-modifying-vinilla-item-help?comment=16
 * http://www.minecraftforge.net/forum/index.php/topic,34641.0.html
 */
public class EdibleSugar {
	/**
	 * The ore name of Sugar.
	 * <p>
	 * This is the same name used by Pam's HarvestCraft.
	 */
	private static final String ORE_NAME_SUGAR = "listAllsugar";

	public static final int SUGAR_FOOD_LEVEL = 0;
	public static final float SUGAR_SATURATION_LEVEL = 0.0f;

	public static final int SUGAR_SPEED_DURATION = 30;
	public static final int SUGAR_SPEED_AMPLIFIER = 0;
	public static final float SUGAR_SPEED_PROBABILITY = 1.0f;

	/**
	 * The recipes to exclude from the replacement process.
	 */
	private static final Set<IRecipe> excludedRecipes = new HashSet<>();

	/**
	 * Excludes a recipe from the replacement process.
	 *
	 * @param recipe The recipe
	 */
	public static void excludeRecipe(IRecipe recipe) {
		excludedRecipes.add(recipe);
	}

	/**
	 * Gets a read-only view of the excluded recipe list.
	 *
	 * @return The list
	 */
	public static Set<IRecipe> getExcludedRecipes() {
		return Collections.unmodifiableSet(excludedRecipes);
	}

	/**
	 * Replaces all recipes that have Sugar as an input or output with recipes that have Edible Sugar as an output and the ore name "listAllsugar" as an input.
	 */
	public static void replaceRecipes() {
		Map<IRecipe, IRecipe> replacedRecipes = RecipeReplacer.replaceInputAndOutput(getExcludedRecipes(), new ItemStack(Items.sugar), new ItemStack(ModItems.edibleSugar), ORE_NAME_SUGAR);
		Logger.info("Replaced %d recipes for Edible Sugar", replacedRecipes.size());

		for (Map.Entry<IRecipe, IRecipe> entry : replacedRecipes.entrySet()) {
			Logger.debug("%s", entry.getKey().getRecipeOutput());
		}
	}

	/**
	 * Adds the recipe to the recipe list and excludes it from the replacement process.
	 *
	 * @param recipe The recipe
	 */
	private static void addAndExcludeRecipe(IRecipe recipe) {
		GameRegistry.addRecipe(recipe);
		excludeRecipe(recipe);
	}

	/**
	 * Add recipes to convert to and from Edible Sugar.
	 */
	public static void addRecipes() {
		addAndExcludeRecipe(new ShapelessOreRecipe(Items.sugar, ModItems.edibleSugar));
		addAndExcludeRecipe(new ShapelessOreRecipe(ModItems.edibleSugar, Items.sugar));
	}
}
