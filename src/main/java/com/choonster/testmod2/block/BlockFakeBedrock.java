package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.IIcon;

public class BlockFakeBedrock extends Block {
	public BlockFakeBedrock() {
		super(Material.rock);

		setUnlocalizedName("fakeBedrock");
		setTextureName("testmod2:candybutton_green");
		setCreativeTab(TestMod2.tab);
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		IIcon temp = super.getIcon(p_149691_1_, p_149691_2_);
		return temp;
	}
}
