package com.choonster.testmod2.init;

import com.choonster.testmod2.References;
import com.choonster.testmod2.block.*;
import com.choonster.testmod2.item.block.ItemCandyButton;
import com.choonster.testmod2.item.block.ItemColoredMod;
import com.choonster.testmod2.tileentity.TileEntityDisplayNameTest;
import com.choonster.testmod2.tileentity.TileEntityOwned;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemDye;
import net.minecraft.tileentity.TileEntity;

public class ModBlocks {
	public static BlockColoredBed[] coloredBeds;
	public static BlockCollisionTest collisionTest;
	public static BlockExample exampleBlock;
	public static BlockSuperTNT superTNT;
	public static BlockCandyButton[] candyButtons;
	public static BlockFakeBedrock fakeBedrock;
	public static BlockCombustible blockCombustible;
	public static BlockDisplayNameTest blockDisplayNameTest;
	public static BlockOwned blockOwned;
	public static BlockCollisionTestFluid blockCollisionTestFluid;
	public static BlockStaticFluid blockStaticFluid;
	public static BlockWaterGrass blockWaterGrass;
	public static BlockFallingNoCollision blockFallingNoCollision;
	public static BlockCollisionPotionEffects blockCollisionPotionEffects;
	public static BlockItemDebugger blockItemDebugger;
	public static BlockRightClickLoot blockRightClickLoot;

	public static void registerBlocks() {
		coloredBeds = new BlockColoredBed[ItemDye.dyeIcons.length];
		for (int colorIndex = 0; colorIndex < coloredBeds.length; colorIndex++) {
			coloredBeds[colorIndex] = new BlockColoredBed(colorIndex);
			registerBlock(coloredBeds[colorIndex], null);
		}

		collisionTest = new BlockCollisionTest();
		GameRegistry.registerBlock(collisionTest, "collisiontest");

		exampleBlock = new BlockExample();
		GameRegistry.registerBlock(exampleBlock, "exampleblock");

		superTNT = new BlockSuperTNT();
		GameRegistry.registerBlock(superTNT, "supertnt");


		candyButtons = new BlockCandyButton[BlockCandyButton.COLOURS.length];
		for (int colourIndex = 0; colourIndex < candyButtons.length; colourIndex++) {
			candyButtons[colourIndex] = new BlockCandyButton(colourIndex);

			// Only register an item for the first colour
			registerBlock(candyButtons[colourIndex], colourIndex == 0 ? ItemCandyButton.class : null);
		}

		fakeBedrock = registerBlock(new BlockFakeBedrock());

		blockCombustible = registerBlock(new BlockCombustible());
		Blocks.fire.setFireInfo(blockCombustible, 100, 0);

		blockDisplayNameTest = registerBlock(new BlockDisplayNameTest());
		registerTileEntity(TileEntityDisplayNameTest.class, "displayNameTest");

		blockOwned = registerBlock(new BlockOwned());
		registerTileEntity(TileEntityOwned.class, "owned");

		blockCollisionTestFluid = registerBlock(new BlockCollisionTestFluid(ModFluids.collisionTest));
		blockStaticFluid = registerBlock(new BlockStaticFluid(ModFluids.staticFluid));

		blockWaterGrass = registerBlock(new BlockWaterGrass(), ItemColoredMod.class, true);

		blockFallingNoCollision = registerBlock(new BlockFallingNoCollision());
		blockCollisionPotionEffects = registerBlock(new BlockCollisionPotionEffects());
		blockItemDebugger = registerBlock(new BlockItemDebugger());
		blockRightClickLoot = registerBlock(new BlockRightClickLoot());
	}

	/**
	 * Get the unlocalised name of a {@link Block} without the "tile." prefix.
	 *
	 * @param block The {@link Block}
	 * @return The unlocalised name without the prefix
	 */
	public static String getStrippedName(Block block) {
		return block.getUnlocalizedName().replaceFirst("tile\\.", "");
	}

	/**
	 * Register a {@link Block} with the default name.
	 *
	 * @param block The {@link Block}
	 * @param <T>   The {@link Block}'s class
	 * @return The {@link Block}
	 */
	private static <T extends Block> T registerBlock(T block) {
		GameRegistry.registerBlock(block, getStrippedName(block));
		return block;
	}

	/**
	 * Register a {@link Block} with the default name and a custom {@link ItemBlock} class.
	 * @param block The {@link Block}
	 * @param itemClass The {@link ItemBlock} class
	 * @param constructorArgs Arguments to pass to the {@code itemClass} constructor
	 * @param <T> The {@link Block}'s class
	 * @return The {@link Block}
	 */
	private static <T extends Block> T registerBlock(T block, Class<? extends ItemBlock> itemClass, Object... constructorArgs) {
		GameRegistry.registerBlock(block, itemClass, getStrippedName(block), constructorArgs);
		return block;
	}

	/**
	 * Register a {@link TileEntity} with the specified mod-specific name.
	 * @param tileEntity The {@link TileEntity}
	 * @param name The name
	 */
	private static void registerTileEntity(Class<? extends TileEntity> tileEntity, String name) {
		GameRegistry.registerTileEntity(tileEntity, References.RESOURCE_PREFIX + name);
	}
}
