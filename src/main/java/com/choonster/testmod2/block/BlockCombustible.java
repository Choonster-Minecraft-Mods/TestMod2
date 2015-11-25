package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCombustible extends Block {
	public BlockCombustible() {
		super(Material.rock);
		setCreativeTab(TestMod2.tab);
		setTextureName("minecraft:stonebrick_chiseled");
		setUnlocalizedName("combustible");
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
		for (ForgeDirection direction : ForgeDirection.VALID_DIRECTIONS) {
			int newX = x + direction.offsetX, newY = y + direction.offsetY, newZ = z + direction.offsetZ;

			if (world.isAirBlock(newX, newY, newZ) && Blocks.fire.canPlaceBlockAt(world, newX, newY, newZ)) {
				world.setBlock(newX, newY, newZ, Blocks.fire);
			}
		}
	}
}
