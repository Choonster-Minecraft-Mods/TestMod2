package com.choonster.testmod2.crafting;

import com.choonster.testmod2.Logger;
import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

public class RecipeReplacer {

	/**
	 * Replaces all recipes that have {@code originalItem} as an input or output and aren't in {@code exclusions} with an equivalent ore recipe,
	 * using {@code replacementOutputItem} as the new output and {@code replacementInputOreName} as the new input.
	 * <p>
	 * Only replaces recipes that use {@link ShapedRecipes}, {@link ShapelessRecipes}, {@link ShapedOreRecipe} or {@link ShapelessOreRecipe}
	 * because any other {@link IRecipe} implementations or subclasses of these classes could have arbitrary behaviour that can't be replicated by a standard ore recipe.
	 *
	 * @param exclusions              A list of recipes to exclude from replacement
	 * @param originalItem            The original item to replace recipes for
	 * @param replacementOutputItem   The new output item
	 * @param replacementInputOreName The new input ore name
	 * @return A map of replaced recipes, with the original recipes as keys and the replacements as values
	 */
	public static Map<IRecipe, IRecipe> replaceInputAndOutput(Set<IRecipe> exclusions, ItemStack originalItem, ItemStack replacementOutputItem, String replacementInputOreName) {
		// originalRecipe -> replacementRecipe
		Map<IRecipe, IRecipe> replacedRecipes = new HashMap<>();

		@SuppressWarnings("unchecked")
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		recipes.replaceAll(recipe -> {
			if (!exclusions.contains(recipe)) {
				try {
					IRecipe replacement = null;

					// Compare classes instead of using instanceof so we don't replace subclasses
					Class<? extends IRecipe> recipeClass = recipe.getClass();
					if (recipeClass == ShapedRecipes.class) {
						replacement = convertShapedRecipe((ShapedRecipes) recipe, originalItem, replacementOutputItem, replacementInputOreName);
					} else if (recipeClass == ShapedOreRecipe.class) {
						replacement = convertShapedRecipe((ShapedOreRecipe) recipe, originalItem, replacementOutputItem, replacementInputOreName);
					} else if (recipeClass == ShapelessRecipes.class) {
						replacement = convertShapelessRecipe((ShapelessRecipes) recipe, originalItem, replacementOutputItem, replacementInputOreName);
					} else if (recipeClass == ShapelessOreRecipe.class) {
						replacement = convertShapelessRecipe((ShapelessOreRecipe) recipe, originalItem, replacementOutputItem, replacementInputOreName);
					}

					if (replacement != null) {
						replacedRecipes.put(recipe, replacement);
						return replacement;
					}
				} catch (UnableToReplaceRecipeException e) {
					// Log it at DEBUG (log file only) rather than ERROR (console and log file) because the most common cause is an empty ore list ingredient.
					// If this is the case, the player can't craft the original recipe anyway; so they probably don't care that it wasn't replaced.
					Logger.debug(e, "Error replacing recipe for original item %s", originalItem);
				}
			}

			return recipe;
		});

		return replacedRecipes;
	}

	// [row][width - 1]
	private static final String[][] SHAPE_STRINGS = {
			{"A", "AB", "ABC"},
			{"D", "DE", "DEF"},
			{"G", "GH", "GHI"}
	};

	/**
	 * Gets an array of shape strings for the specified recipe width and height.
	 *
	 * @param recipeHeight The recipe height
	 * @param recipeWidth  The recipe width
	 * @return The shape strings
	 */
	private static String[] getShapeStrings(Object[] ingredients, int recipeHeight, int recipeWidth) {
		String[] result = new String[recipeHeight];
		for (int row = 0; row < recipeHeight; row++) {
			char[] shapeString = SHAPE_STRINGS[row][recipeWidth - 1].toCharArray();

			for (int col = 0; col < recipeWidth; col++) {
				if (ingredients[row * recipeWidth + col] == null) { // If this ingredient is null,
					shapeString[col] = ' '; // Replace it with a space in the shape string
				}
			}

			result[row] = String.valueOf(shapeString);
		}
		return result;
	}

	/**
	 * Replaces the {@code ingredient} with {@code replacementInputOreName} if it's {@code originalItem} or the corresponding ore name if it's a list of ores.
	 *
	 * @param ingredient              The ingredient
	 * @param originalItem            The item to replace
	 * @param replacementInputOreName The new input ore name
	 * @return The replacement, or {@code ingredient} if there was no replacement needed
	 * @throws OreDictStuff.InvalidOreException When no matching ore name is found for an ore list
	 */
	private static Object replaceIngredient(Object ingredient, ItemStack originalItem, String replacementInputOreName) throws OreDictStuff.InvalidOreException {
		if (ingredient instanceof ItemStack && OreDictionary.itemMatches(originalItem, (ItemStack) ingredient, true)) { // If the ingredient is the item to replace,
			return replacementInputOreName; // Return the replacement ore name
		} else if (ingredient instanceof ArrayList) { // If the ingredient is a list of ores,
			@SuppressWarnings("unchecked")
			List<ItemStack> oreList = (List<ItemStack>) ingredient;
			return OreDictStuff.getOreNameForOreList(oreList); // Return the ore name
		} else {
			return ingredient; // Else return the ingredient
		}
	}

	/**
	 * Creates an input list for a {@link ShapedOreRecipe}, using {@link #replaceIngredient(Object, ItemStack, String)} to process each ingredient.
	 *
	 * @param ingredients             The original recipe's ingredient list
	 * @param recipeHeight            The original recipe's height
	 * @param recipeWidth             The original recipe's width
	 * @param originalItem            The item to replace
	 * @param replacementInputOreName The new input ore name
	 * @return The input list
	 * @throws OreDictStuff.InvalidOreException When no matching ore name is found for an ore list ingredient
	 */
	private static List<Object> createShapedInputs(Object[] ingredients, int recipeHeight, int recipeWidth, ItemStack originalItem, String replacementInputOreName) throws OreDictStuff.InvalidOreException {
		String[] shapeStrings = getShapeStrings(ingredients, recipeHeight, recipeWidth);
		String fullShapeString = StringUtils.join(shapeStrings);

		List<Object> input = new ArrayList<>();
		input.add(shapeStrings); // Add the shape strings

		for (int i = 0; i < ingredients.length; i++) { // For each ingredient,
			Object ingredient = ingredients[i];
			if (ingredient != null) {
				char inputChar = fullShapeString.charAt(i);
				input.add(inputChar); // Add the character for the ingredient
				input.add(replaceIngredient(ingredient, originalItem, replacementInputOreName)); // Add the replacement ingredient
			}
		}

		return input;
	}

	/**
	 * Creates an input list for a {@link ShapelessOreRecipe}, using {@link #replaceIngredient(Object, ItemStack, String)} to process each ingredient.
	 *
	 * @param ingredients             The original recipe's ingredient list
	 * @param originalItem            The item to replace
	 * @param replacementInputOreName The new input ore name
	 * @return The input list
	 * @throws OreDictStuff.InvalidOreException When no matching ore name is found for an ore list ingredient
	 */
	private static List<Object> createShapelessInputs(List<Object> ingredients, ItemStack originalItem, String replacementInputOreName) throws OreDictStuff.InvalidOreException {
		List<Object> input = new ArrayList<>();

		for (Object ingredient : ingredients) { // For each ingredient,
			input.add(replaceIngredient(ingredient, originalItem, replacementInputOreName)); // Add the replacement ingredient
		}

		return input;
	}

	/**
	 * Creates a {@link ShapedOreRecipe} from a {@link ShapedRecipes}, replacing {@code originalItem} with {@code replacementOutputItem} or {@code replacementInputOreName} as appropriate.
	 *
	 * @param recipe                  The original recipe
	 * @param originalItem            The item to replace
	 * @param replacementOutputItem   The new output item
	 * @param replacementInputOreName The new input ore name
	 * @return The new recipe, or {@code null} if no replacement was needed
	 * @throws UnableToReplaceRecipeException When the recipe couldn't be replaced
	 */
	private static IRecipe convertShapedRecipe(ShapedRecipes recipe, ItemStack originalItem, ItemStack replacementOutputItem, String replacementInputOreName) throws UnableToReplaceRecipeException {
		try {
			List<Object> input = createShapedInputs(recipe.recipeItems, recipe.recipeHeight, recipe.recipeWidth, originalItem, replacementInputOreName);

			boolean inputMatches = input.contains(replacementInputOreName);

			boolean outputMatches = OreDictionary.itemMatches(originalItem, recipe.getRecipeOutput(), true);
			ItemStack output = outputMatches ? replacementOutputItem : recipe.getRecipeOutput();

			return (inputMatches || outputMatches) ? new ShapedOreRecipe(output, input.toArray()) : null;
		} catch (OreDictStuff.InvalidOreException e) {
			throw new UnableToReplaceRecipeException(recipe, recipe.recipeItems, e);
		}
	}

	// We need access to the height and width of the recipe to build the shape strings for the replacement, but there are no getters so we have to use reflection.
	private static final Field SHAPED_ORE_RECIPE_HEIGHT = ReflectionHelper.findField(ShapedOreRecipe.class, "height");
	private static final Field SHAPED_ORE_RECIPE_WIDTH = ReflectionHelper.findField(ShapedOreRecipe.class, "width");

	/**
	 * Creates a {@link ShapedOreRecipe} from a {@link ShapedOreRecipe}, replacing {@code originalItem} with {@code replacementOutputItem} or {@code replacementInputOreName} as appropriate.
	 *
	 * @param recipe                  The original recipe
	 * @param originalItem            The item to replace
	 * @param replacementOutputItem   The new output item
	 * @param replacementInputOreName The new input ore name
	 * @return The new recipe, or {@code null} if no replacement was needed
	 * @throws UnableToReplaceRecipeException When the recipe couldn't be replaced
	 */
	private static IRecipe convertShapedRecipe(ShapedOreRecipe recipe, ItemStack originalItem, ItemStack replacementOutputItem, String replacementInputOreName) throws UnableToReplaceRecipeException {
		try {
			List<Object> input = createShapedInputs(recipe.getInput(), SHAPED_ORE_RECIPE_HEIGHT.getInt(recipe), SHAPED_ORE_RECIPE_WIDTH.getInt(recipe), originalItem, replacementInputOreName);
			boolean inputMatches = input.contains(replacementInputOreName);

			boolean outputMatches = OreDictionary.itemMatches(originalItem, recipe.getRecipeOutput(), true);
			ItemStack output = outputMatches ? replacementOutputItem : recipe.getRecipeOutput();

			return (inputMatches || outputMatches) ? new ShapedOreRecipe(output, input.toArray()) : null;
		} catch (OreDictStuff.InvalidOreException | IllegalAccessException e) {
			throw new UnableToReplaceRecipeException(recipe, recipe.getInput(), e);
		}
	}

	/**
	 * Creates a {@link ShapelessOreRecipe} from a {@link ShapelessRecipes}, replacing {@code originalItem} with {@code replacementOutputItem} or {@code replacementInputOreName} as appropriate.
	 *
	 * @param recipe                  The original recipe
	 * @param originalItem            The item to replace
	 * @param replacementOutputItem   The new output item
	 * @param replacementInputOreName The new input ore name
	 * @return The new recipe, or {@code null} if no replacement was needed
	 * @throws UnableToReplaceRecipeException When the recipe couldn't be replaced
	 */
	private static IRecipe convertShapelessRecipe(ShapelessRecipes recipe, ItemStack originalItem, ItemStack replacementOutputItem, String replacementInputOreName) throws UnableToReplaceRecipeException {
		try {
			@SuppressWarnings("unchecked")
			List<Object> input = createShapelessInputs(recipe.recipeItems, originalItem, replacementInputOreName);
			boolean inputMatches = input.contains(replacementInputOreName);

			boolean outputMatches = OreDictionary.itemMatches(originalItem, recipe.getRecipeOutput(), true);
			ItemStack output = outputMatches ? replacementOutputItem : recipe.getRecipeOutput();

			return (inputMatches || outputMatches) ? new ShapelessOreRecipe(output, input.toArray()) : null;
		} catch (OreDictStuff.InvalidOreException e) {
			throw new UnableToReplaceRecipeException(recipe, recipe.recipeItems, e);
		}
	}

	/**
	 * Creates a {@link ShapelessOreRecipe} from a {@link ShapelessOreRecipe}, replacing {@code originalItem} with {@code replacementOutputItem} or {@code replacementInputOreName} as appropriate.
	 *
	 * @param recipe                  The original recipe
	 * @param originalItem            The item to replace
	 * @param replacementOutputItem   The new output item
	 * @param replacementInputOreName The new input ore name
	 * @return The new recipe, or {@code null} if no replacement was needed
	 * @throws UnableToReplaceRecipeException When the recipe couldn't be replaced
	 */
	private static IRecipe convertShapelessRecipe(ShapelessOreRecipe recipe, ItemStack originalItem, ItemStack replacementOutputItem, String replacementInputOreName) throws UnableToReplaceRecipeException {
		try {
			List<Object> input = createShapelessInputs(recipe.getInput(), originalItem, replacementInputOreName);
			boolean inputMatches = input.contains(replacementInputOreName);

			boolean outputMatches = OreDictionary.itemMatches(originalItem, recipe.getRecipeOutput(), true);
			ItemStack output = outputMatches ? replacementOutputItem : recipe.getRecipeOutput();

			return (inputMatches || outputMatches) ? new ShapelessOreRecipe(output, input.toArray()) : null;
		} catch (OreDictStuff.InvalidOreException e) {
			throw new UnableToReplaceRecipeException(recipe, recipe.getInput(), e);
		}
	}

	/**
	 * Thrown when a recipe can't be replaced for some reason. Creates a human-readable message from the original recipe's class, output and inputs.
	 */
	public static class UnableToReplaceRecipeException extends Exception {
		public UnableToReplaceRecipeException(IRecipe recipe, List<Object> input, Throwable cause) {
			super(getMessageFromRecipe(recipe, input), cause);
		}

		public UnableToReplaceRecipeException(IRecipe recipe, Object[] input, Throwable cause) {
			this(recipe, Lists.newArrayList(input), cause);
		}

		private static String getMessageFromRecipe(IRecipe recipe, List<Object> input) {
			return "Class: " + recipe.getClass().getSimpleName() +
					" - Output: " + recipe.getRecipeOutput() +
					" - Input: " + input;
		}
	}
}
