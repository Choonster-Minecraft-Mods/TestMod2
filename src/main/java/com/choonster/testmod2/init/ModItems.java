package com.choonster.testmod2.init;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.item.*;
import com.choonster.testmod2.item.block.ItemCandyButton;
import com.choonster.testmod2.item.block.ItemColoredBed;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.common.util.EnumHelper;

import static com.choonster.testmod2.tweak.ediblesugar.EdibleSugar.*;

public class ModItems {
	public static ItemColoredBed coloredBed;
	public static ItemCollisionTest collisionTest;
	public static ItemFireWand fireWand;
	public static ItemSnowballNoGrav snowballNoGrav;
	public static ItemCandyButton candyButton;
	public static ItemFoodAdder foodAdder;
	public static ItemFoodSubtractor foodSubtractor;
	public static ItemExhaustionReset foodExhaustionResetter;
	public static ItemStaff staff;
	public static ItemChiselAndHammer chiselAndHammer;
	public static ItemDirtyDust dirtyDust;
	public static ItemRecord solarisRecord;
	public static ItemEarthWand earthWand;
	public static ItemContainerTest containerTest;
	public static ItemBarrelBomb barrelBomb;
	public static ItemContainerUnbreaking containerUnbreaking;
	public static ItemContainerBreaking containerBreaking;
	public static ItemGiver giver;
	public static ItemFinder finder;
	public static ItemClearer clearer;
	public static ItemModMonsterPlacer spawnEgg;
	public static ItemUseCountTest useCountTest;
	public static ItemXPSubtractor xpSubtractor;
	public static ItemPotionApplier potionApplier;
	public static ItemRecordRawk recordRawk;
	public static Item unicode;
	public static ItemFood edibleSugar;
	public static ItemHarvestSword woodenHarvestSword;
	public static ItemHarvestSword diamondHarvestSword;
	public static Item canOpener;
	public static ItemCannedFood cannedPeaches;
	public static ItemFood cannedPeachesOpen;
	public static ItemWarHammer diamondWarHammer;
	public static ItemModPotion potion;
	public static ItemBlockDebugger blockDebugger;
	public static Item fuel;
	public static Item fuel2;

	public static final Item.ToolMaterial TOOL_MATERIAL_GLOWSTONE = EnumHelper.addToolMaterial("glowstone", 1, 5, 0.5f, 1.0f, 10).setRepairItem(new ItemStack(Items.glowstone_dust));

	public static void registerItems() {
		coloredBed = registerItem(new ItemColoredBed());
		collisionTest = registerItem(new ItemCollisionTest());
		fireWand = registerItem(new ItemFireWand());
		snowballNoGrav = registerItem(new ItemSnowballNoGrav());
		candyButton = (ItemCandyButton) Item.getItemFromBlock(ModBlocks.candyButtons[0]);
		foodAdder = registerItem(new ItemFoodAdder());
		foodSubtractor = registerItem(new ItemFoodSubtractor());
		foodExhaustionResetter = registerItem(new ItemExhaustionReset());
		staff = registerItem(new ItemStaff());
		chiselAndHammer = registerItem(new ItemChiselAndHammer());
		dirtyDust = registerItem(new ItemDirtyDust());
		solarisRecord = registerItem(new ItemRecordSolaris(), "recordSolaris");
		earthWand = registerItem(new ItemEarthWand());
		containerTest = registerItem(new ItemContainerTest());
		barrelBomb = registerItem(new ItemBarrelBomb());
		containerUnbreaking = registerItem(new ItemContainerUnbreaking());
		containerBreaking = registerItem(new ItemContainerBreaking());
		giver = registerItem(new ItemGiver());
		finder = registerItem(new ItemFinder());
		clearer = registerItem(new ItemClearer());
		spawnEgg = registerItem(new ItemModMonsterPlacer());
		useCountTest = registerItem(new ItemUseCountTest());
		xpSubtractor = registerItem(new ItemXPSubtractor());
		potionApplier = registerItem(new ItemPotionApplier());
		recordRawk = registerItem(new ItemRecordRawk(), "recordRawk");
		unicode = registerItem(new Item().setUnlocalizedName("unicode").setTextureName("minecraft:apple").setCreativeTab(TestMod2.tab));

		edibleSugar = (ItemFood) registerItem(
				new ItemFood(SUGAR_FOOD_LEVEL, SUGAR_SATURATION_LEVEL, false)
						.setPotionEffect(Potion.moveSpeed.getId(), SUGAR_SPEED_DURATION, SUGAR_SPEED_AMPLIFIER, SUGAR_SPEED_PROBABILITY)
						.setAlwaysEdible().setPotionEffect(PotionHelper.sugarEffect)
						.setUnlocalizedName("edibleSugar").setTextureName("sugar").setCreativeTab(TestMod2.tab)
		);

		woodenHarvestSword = (ItemHarvestSword) registerItem(new ItemHarvestSword(Item.ToolMaterial.WOOD).setUnlocalizedName("harvestSwordWood").setTextureName("wood_sword"));
		diamondHarvestSword = (ItemHarvestSword) registerItem(new ItemHarvestSword(Item.ToolMaterial.EMERALD).setUnlocalizedName("harvestSwordDiamond").setTextureName("diamond_sword"));

		diamondWarHammer = (ItemWarHammer) registerItem(new ItemWarHammer(Item.ToolMaterial.EMERALD).setUnlocalizedName("warHammerDiamond").setTextureName("diamond_sword"));

		canOpener = registerItem(new Item().setMaxDurability(19).setMaxStackSize(1).setUnlocalizedName("canOpener").setTextureName("testmod2:canOpener").setCreativeTab(TestMod2.tab));
		cannedPeachesOpen = (ItemFood) registerItem(new ItemFood(8, 0.6f, false).setUnlocalizedName("cannedPeachesOpen").setTextureName("testmod2:cannedPeachesOpen").setCreativeTab(TestMod2.tab));
		cannedPeaches = (ItemCannedFood) registerItem(new ItemCannedFood().setCanOpener(canOpener).setOpenCan(new ItemStack(cannedPeachesOpen)).setUnlocalizedName("cannedPeaches").setTextureName("testmod2:cannedPeaches").setCreativeTab(TestMod2.tab));

		potion = registerItem(new ItemModPotion());
		blockDebugger = registerItem(new ItemBlockDebugger());
		fuel = registerItem(new Item().setUnlocalizedName(References.RESOURCE_PREFIX + "fuel").setTextureName("minecraft:coal").setCreativeTab(TestMod2.tab));
		fuel2 = registerItem(new Item().setUnlocalizedName(References.RESOURCE_PREFIX + "fuel2").setTextureName("minecraft:charcoal").setCreativeTab(TestMod2.tab));
	}

	/**
	 * Get the unlocalised name of an item without the "item." prefix.
	 *
	 * @param item The item
	 * @return The unlocalised name without the prefix
	 */
	public static String getStrippedName(Item item) {
		return item.getUnlocalizedName().replaceFirst("item\\.", "").replaceFirst(References.RESOURCE_PREFIX, "");
	}

	/**
	 * Register an {@link Item} with the default name.
	 *
	 * @param item The {@link Item}
	 * @param <T>  The {@link Item}'s class
	 * @return The {@link Item}
	 */
	private static <T extends Item> T registerItem(T item) {
		return registerItem(item, getStrippedName(item));
	}

	/**
	 * Register an {@link Item} with the specified name.
	 * @param item The {@link Item}
	 * @param name The name
	 * @param <T> The {@link Item}'s class
	 * @return The {@link Item}
	 */
	private static <T extends Item> T registerItem(T item, String name) {
		GameRegistry.registerItem(item, name);
		return item;
	}
}
