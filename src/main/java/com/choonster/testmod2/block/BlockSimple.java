package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * A simple block with no custom behaviour.
 *
 * @author Choonster
 */
public class BlockSimple extends Block {
	public BlockSimple(Material materialIn) {
		super(materialIn);
		setCreativeTab(TestMod2.tab);
	}
}
