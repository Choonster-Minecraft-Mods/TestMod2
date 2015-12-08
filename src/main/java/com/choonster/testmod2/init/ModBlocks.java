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
		coloredBeds = new BlockColoredBed[16];
		for (int colorIndex = 0; colorIndex < coloredBeds.length; colorIndex++) {
			String name = "coloredBed." + References.COLORS[colorIndex];
			coloredBeds[colorIndex] = (BlockColoredBed) new BlockColoredBed(colorIndex).setUnlocalizedName(name);
			GameRegistry.registerBlock(coloredBeds[colorIndex], null, name);
		}

		collisionTest = new BlockCollisionTest();
		GameRegistry.registerBlock(collisionTest, "collisiontest");

		exampleBlock = new BlockExample();
		GameRegistry.registerBlock(exampleBlock, "exampleblock");

		superTNT = new BlockSuperTNT();
		GameRegistry.registerBlock(superTNT, "supertnt");


		String[] colours = BlockCandyButton.COLOURS;
		candyButtons = new BlockCandyButton[colours.length];
		for (int i = 0; i < candyButtons.length; i++) {
			candyButtons[i] = new BlockCandyButton(i);

			// Even though each instance of BlockCandyButton will create its own instance of ItemCandyButton,
			// only the first one is ever used.
			GameRegistry.registerBlock(candyButtons[i], ItemCandyButton.class, "candyButton_" + colours[i], i);
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
