package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public class BlockStaticFluid extends BlockFluidFinite {

	public BlockStaticFluid(Fluid fluid) {
		super(fluid, Material.water);
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName(fluid.getUnlocalizedName());
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.flowing_water.getIcon(side, meta);
	}

	@Override
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
		return 90019001;
	}
}
