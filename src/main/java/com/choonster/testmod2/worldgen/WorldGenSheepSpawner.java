package com.choonster.testmod2.worldgen;

import com.choonster.testmod2.Logger;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import java.util.Random;

// Generates a sheep spawner in the overworld on the surface at 0,0 in chunks where the x and z coordinates are divisible by 16
// Test for this thread:
// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2458637-how-do-i-create-a-custom-one-use-mob-entity
public class WorldGenSheepSpawner implements IWorldGenerator {
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.dimensionId == 0 && chunkX % 16 == 0 && chunkZ % 16 == 0) {
			int x = chunkX * 16;
			int z = chunkZ * 16;
			int y = world.getTopSolidOrLiquidBlock(x, z) + 1;

			world.setBlock(x, y, z, Blocks.mob_spawner);
			TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(x, y, z);
			spawner.func_145881_a().setEntityName("Sheep");
			Logger.info("Sheep spawner at %d,%d,%d", x, y, z);
		}
	}
}
