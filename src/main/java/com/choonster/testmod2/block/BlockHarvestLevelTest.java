package com.choonster.testmod2.block;

import net.minecraft.block.material.Material;

/**
 * A block with a custom harvest level.
 *
 * @author Choonster
 */
public class BlockHarvestLevelTest extends BlockSimple {
	public BlockHarvestLevelTest(Material materialIn, String name, String harvestTool, int harvestLevel) {
		super(materialIn);
		setUnlocalizedName(name);
		setHarvestLevel(harvestTool, harvestLevel);
		setHardness(4);
	}
}
