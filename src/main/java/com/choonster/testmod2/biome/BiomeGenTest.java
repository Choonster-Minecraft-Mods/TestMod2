package com.choonster.testmod2.biome;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Iterator;
import java.util.Random;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2440392-removing-mobs-from-my-biome
public class BiomeGenTest extends BiomeGenBase {
	private boolean logged = false;

	@SuppressWarnings("unchecked")
	public BiomeGenTest(int id) {
		super(id);

		topBlock = Blocks.hay_block;
		fillerBlock = Blocks.brick_block;

		for (Iterator<SpawnListEntry> iterator = spawnableMonsterList.iterator(); iterator.hasNext(); ) {
			SpawnListEntry entry = iterator.next();
			if (entry.entityClass.equals(EntityCreeper.class) || entry.entityClass.equals(EntityZombie.class)) {
				iterator.remove();
			}
		}

		setBiomeName("Test");
	}

	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blockArray, byte[] metaArray, int x, int z, double stoneNoise) {
		super.genTerrainBlocks(world, random, blockArray, metaArray, x, z, stoneNoise);

		if (!logged) {
			logged = true;
			FMLLog.bigWarning("Generating biome test at %d, %d", x, z);
		}
	}
}
