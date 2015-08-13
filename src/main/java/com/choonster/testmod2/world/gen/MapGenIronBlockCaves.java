package com.choonster.testmod2.world.gen;

import com.choonster.testmod2.Logger;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.MapGenBase;

public class MapGenIronBlockCaves extends MapGenBase {
	/**
	 * Converts chunk-relative coordinates into an index for the Block array
	 *
	 * @param x The chunk-relative x coordinate (0-15)
	 * @param y The y coordinate (0-255)
	 * @param z The chunk-relative y coordinate (0-15)
	 * @return The index for the Block array
	 */
	private int coordsToIndex(int x, int y, int z) {
		return (x * 16 + z) * 256 + y;
	}

	@Override
	protected void func_151538_a(World world, int chunkX, int chunkZ, int centreX, int centreZ, Block[] blocks) {
		if (rand.nextInt(100) < 10) return;

		int x = rand.nextInt(16);
		int y = rand.nextInt(25);
		int z = rand.nextInt(16);

		int index = coordsToIndex(x, y, z);

		if (blocks[index] == null || blocks[index] == Blocks.air) {
			Logger.info("Generating iron block at %d,%d,%d", chunkX * 16 + x, y, chunkZ * 16 + z);
			blocks[index] = Blocks.iron_block;
		}
	}
}
