package com.choonster.testmod2.init;

import com.choonster.testmod2.item.ItemModPotion;
import com.choonster.testmod2.recipe.ShapedEnchantingRecipe;
import com.choonster.testmod2.recipe.ShapelessNBTRecipe;
import com.choonster.testmod2.tweak.ediblesugar.EdibleSugar;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ModRecipes {

	/**
	 * Called during init to register our custom recipe classes and add our recipes
	 */
	public static void registerRecipes() {
		registerRecipeClasses();
		addOreDictionaryEntries();
		addCraftingRecipes();
		addSmeltingRecipes();

		EdibleSugar.addRecipes();
	}

	private static void addOreDictionaryEntries() {
		OreDictionary.registerOre("listAllsugar", Items.sugar); // Use same ore name as Pam's HarvestCraft
		OreDictionary.registerOre("listAllsugar", ModItems.edibleSugar);
	}

	private static void registerRecipeClasses() {
		RecipeSorter.register("testmod2:shapedenchanting", ShapedEnchantingRecipe.class, RecipeSorter.Category.SHAPED, "after:forge:shapedore");
		RecipeSorter.register("testmod2:shapelessnbt", ShapelessNBTRecipe.class, RecipeSorter.Category.SHAPELESS, "after:forge:shapelessore");
	}

	private static void addCraftingRecipes() {
		// -- Enchanting Recipe --
		GameRegistry.addRecipe(new ShapedEnchantingRecipe(Items.book, 30, " A ", "ABA", " A ", 'A', "ingotGold", 'B', Items.book));

		// -- Staff Upgrade --
		ItemStack staffLevel1 = new ItemStack(ModItems.staff, 1, 0); // Metadata 0 = level 1
		ItemStack staffLevel2 = new ItemStack(ModItems.staff, 1, 1); // Metadata 1 = level 2

		GameRegistry.addRecipe(staffLevel2, "GGG", "GSG", "GGG", 'G', Items.gold_nugget, 'S', staffLevel1); // Level 1 + Gold Nuggets = Level 2

		// -- Container Items --
		GameRegistry.addShapelessRecipe(new ItemStack(Items.fish), new ItemStack(ModItems.containerTest, 1, OreDictionary.WILDCARD_VALUE));

		// The unbreaking container doesn't change metadata values, so just use it directly as an ingredient
		GameRegistry.addShapelessRecipe(new ItemStack(Items.blaze_rod), ModItems.containerUnbreaking);

		// The breaking container does change metadata values, so use an ItemStack with OreDictionary.WILDCARD_VALUE as the metadata value as an ingredient
		GameRegistry.addShapelessRecipe(new ItemStack(Items.blaze_rod), new ItemStack(ModItems.containerBreaking, 1, OreDictionary.WILDCARD_VALUE));

		// -- Potion Recipes --
		final double defaultBlindnessDuration = ItemModPotion.getDefaultDuration(Potion.blindness);

		PotionEffect[] blindnessEffects = new PotionEffect[]{
				new PotionEffect(Potion.blindness.getId(), (int) Math.round(defaultBlindnessDuration)),
				new PotionEffect(Potion.blindness.getId(), (int) Math.round(defaultBlindnessDuration * ItemModPotion.DURATION_MULTIPLIER_EXTENDED)),
		};

		List<ItemStack> blindnessPotions = Stream.of(blindnessEffects).map(potionEffect -> ModItems.potion.getCustomPotion(false, potionEffect)).collect(Collectors.toList());

		ItemStack blindnessPotionBase = blindnessPotions.get(0);
		addShapelessNBTRecipe(blindnessPotionBase, Items.potionitem, Items.nether_wart, Items.coal);
		addShapelessNBTRecipe(blindnessPotions.get(1), blindnessPotionBase, Items.redstone);

		PotionEffect[] blindnessSplashEffects = new PotionEffect[]{
				new PotionEffect(Potion.blindness.getId(), (int) Math.round(defaultBlindnessDuration * ItemModPotion.DURATION_MULTIPLIER_SPLASH)),
				new PotionEffect(Potion.blindness.getId(), (int) Math.round(defaultBlindnessDuration * ItemModPotion.DURATION_MULTIPLIER_EXTENDED * ItemModPotion.DURATION_MULTIPLIER_SPLASH)),
		};

		for (int i = 0; i < blindnessSplashEffects.length; i++) {
			ItemStack blindnessPotion = blindnessPotions.get(i);
			ItemStack blindnessSplashPotion = ModItems.potion.getCustomPotion(true, blindnessSplashEffects[i]);
			addShapelessNBTRecipe(blindnessSplashPotion, blindnessPotion, Items.gunpowder);
		}

		// -- Miscellaneous Recipes --
		GameRegistry.addRecipe(new ItemStack(Items.stick, 1), "X ", "X ", 'X', Blocks.obsidian);
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.stone_button), "G S", 'G', Items.gold_nugget, 'S', Blocks.stone);

		GameRegistry.addRecipe(new ItemStack(Blocks.diamond_block), "PP", "PP", 'P', Blocks.planks);
		GameRegistry.addRecipe(new ItemStack(Blocks.diamond_block), "SSS", 'S', new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addShapelessRecipe(new ItemStack(Items.record_13), new ItemStack(Blocks.tallgrass, 1, 1), new ItemStack(Blocks.tallgrass, 1, 1));
	}

	private static void addShapelessNBTRecipe(ItemStack result, Object... recipe) {
		GameRegistry.addRecipe(new ShapelessNBTRecipe(result, recipe));
	}

	private static void addSmeltingRecipes() {
		GameRegistry.addSmelting(Blocks.coal_block, new ItemStack(Blocks.obsidian, 1), 20.0f);
	}

	/**
	 * Called during postInit to replace and remove recipes
	 */
	public static void replaceAndRemoveRecipes() {
		removeRecipes();

		EdibleSugar.replaceRecipes();
	}

	private static void removeRecipes() {

	}
}
