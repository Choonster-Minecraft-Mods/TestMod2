package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockFakeBedrock extends Block {
	public BlockFakeBedrock() {
		super(Material.rock);

		setUnlocalizedName("fakeBedrock");
		setTextureName("testmod2:candybutton_green");
		setCreativeTab(TestMod2.tab);
	}
}
