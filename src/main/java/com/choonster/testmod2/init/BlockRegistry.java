package com.choonster.testmod2.init;

import com.choonster.testmod2.References;
import com.choonster.testmod2.block.*;
import com.choonster.testmod2.item.ItemCandyButton;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;

public class BlockRegistry {
	public static Block[] coloredBeds;
	public static Block collisionTest;
	public static Block exampleBlock;
	public static Block superTNT;
	public static Block[] candyButtons;
	public static Block fakeBedrock;
	public static Block blockCombustible;

	public static void registerBlocks() {
		/*
		coloredBeds = new Block[16];
		for (int colorIndex = 0; colorIndex < coloredBeds.length; colorIndex++) {
			String name = "coloredBed." + References.COLORS[colorIndex];
			coloredBeds[colorIndex] = new BlockColoredBed(colorIndex).setBlockName(name);
			GameRegistry.registerBlock(coloredBeds[colorIndex], name);
		}

		collisionTest = new BlockCollisionTest();
		GameRegistry.registerBlock(collisionTest, "collisiontest");

		exampleBlock = new BlockExample();
		GameRegistry.registerBlock(exampleBlock, "exampleblock");

		superTNT = new BlockSuperTNT();
		GameRegistry.registerBlock(superTNT, "supertnt");
		*/

		String[] colours = BlockCandyButton.COLOURS;
		candyButtons = new Block[colours.length];
		for (int i = 0; i < candyButtons.length; i++) {
			candyButtons[i] = new BlockCandyButton(i);

			// Even though each instance of BlockCandyButton will create its own instance of ItemCandyButton,
			// only the first one is ever used.
			GameRegistry.registerBlock(candyButtons[i], ItemCandyButton.class, "candyButton_" + colours[i], i);
		}

		fakeBedrock = new BlockFakeBedrock();
		GameRegistry.registerBlock(fakeBedrock, "fakeBedrock");

		blockCombustible = new BlockCombustible();
		GameRegistry.registerBlock(blockCombustible, "combustible");
		Blocks.fire.setFireInfo(blockCombustible, 100, 0);
	}
}
