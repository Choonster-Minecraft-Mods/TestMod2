package com.choonster.testmod2.biome;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenPlains;

import java.util.Random;

/**
 * A plains-like biome that uses Nether Brick instead of Stone for its underground terrain.
 * <p>
 * Note that Lava lakes generate Stone around them after the biome generates its terrain, so there will be small amounts of Stone in the biome.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2492418-biome-difficulty
 */
public class BiomeGenTerrainReplacement extends BiomeGenPlains {
	public BiomeGenTerrainReplacement(int id) {
		super(id);

		topBlock = Blocks.coal_block;
		fillerBlock = Blocks.end_stone;

		setColor(9286496);
		setBiomeName("Terrain Replacement Biome");
	}


	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metadataArray, int genX, int genZ, double stoneNoise) {
		genCustomTerrain(world, random, blocks, metadataArray, genX, genZ, stoneNoise);
	}

	/**
	 * Adapted from {@link BiomeGenBase#genBiomeTerrain}. Does the same thing but generates Nether Brick instead of Stone.
	 *
	 * @param world         The World being generated
	 * @param random        The Random object
	 * @param blocks        The Block array to populate
	 * @param metadataArray The metadata array to populate
	 * @param genX          The x coordinate to generate
	 * @param genZ          The z coordinate to generate
	 * @param stoneNoise    The stone noise for these coordinates
	 */
	public void genCustomTerrain(World world, Random random, Block[] blocks, byte[] metadataArray, int genX, int genZ, double stoneNoise) {
		Block topBlock = this.topBlock;
		byte metadata = (byte) (this.field_150604_aj & 255);
		Block fillerBlock = this.fillerBlock;
		int k = -1;
		int l = (int) (stoneNoise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
		int x = genX & 15;
		int z = genZ & 15;
		int multiplier = blocks.length / 256;

		for (int y = 255; y >= 0; --y) {
			int index = (z * 16 + x) * multiplier + y;

			if (y <= random.nextInt(5)) {
				blocks[index] = Blocks.bedrock;
			} else {
				Block existingBlock = blocks[index];

				if (existingBlock != null && existingBlock.getMaterial() != Material.air) {
					if (existingBlock == Blocks.stone) {
						blocks[index] = Blocks.nether_brick;

						if (k == -1) {
							if (l <= 0) {
								topBlock = null;
								metadata = 0;
								fillerBlock = Blocks.nether_brick;
							} else if (y >= 59 && y <= 64) {
								topBlock = this.topBlock;
								metadata = (byte) (this.field_150604_aj & 255);
								fillerBlock = this.fillerBlock;
							}

							if (y < 63 && (topBlock == null || topBlock.getMaterial() == Material.air)) {
								if (this.getFloatTemperature(genX, y, genZ) < 0.15F) {
									topBlock = Blocks.ice;
									metadata = 0;
								} else {
									topBlock = Blocks.water;
									metadata = 0;
								}
							}

							k = l;

							if (y >= 62) {
								blocks[index] = topBlock;
								metadataArray[index] = metadata;
							} else if (y < 56 - l) {
								topBlock = null;
								fillerBlock = Blocks.nether_brick;
								blocks[index] = Blocks.gravel;
							} else {
								blocks[index] = fillerBlock;
							}
						} else if (k > 0) {
							--k;
							blocks[index] = fillerBlock;

							if (k == 0 && fillerBlock == Blocks.sand) {
								k = random.nextInt(4) + Math.max(0, y - 63);
								fillerBlock = Blocks.sandstone;
							}
						}
					}
				} else {
					k = -1;
				}
			}
		}
	}
}
