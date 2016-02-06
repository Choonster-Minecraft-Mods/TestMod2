package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * A flammable block.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2607187-how-to-apply-flammable-properties-to-blocks
 *
 * @author Choonster
 */
public class BlockFlammable extends Block {
	private final int encouragement;
	private final int flammability;

	public BlockFlammable(Material materialIn, int encouragement, int flammability) {
		super(materialIn);
		this.encouragement = encouragement;
		this.flammability = flammability;
		setCreativeTab(TestMod2.tab);
	}

	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return encouragement;
	}

	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return flammability;
	}
}
