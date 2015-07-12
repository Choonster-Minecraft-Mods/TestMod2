package com.choonster.testmod2.init;

import com.choonster.testmod2.biome.BiomeGenTest;
import com.choonster.testmod2.config.Config;
import com.google.common.collect.Queues;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import java.util.Queue;

import static net.minecraftforge.common.BiomeDictionary.Type.COLD;

public class ModBiomes {
	public static BiomeGenTest biomeGenTest;

	public static void registerBiomes() {
		if (Config.disableOtherBiomes) {
			removeOtherBiomes();
		}

		biomeGenTest = reigsterBiome(new BiomeGenTest(Config.testBiomeID), BiomeManager.BiomeType.ICY, 10000, COLD);
	}

	private static <T extends BiomeGenBase> T reigsterBiome(T biome, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
		BiomeDictionary.registerBiomeType(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));

		return biome;
	}

	private static void removeOtherBiomes() {
		for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values()) {
			Queue<BiomeManager.BiomeEntry> entries = Queues.newArrayDeque(BiomeManager.getBiomes(type));

			if (type != BiomeManager.BiomeType.ICY) {
				entries.poll(); // Leave one entry per type for types other than ICY
			}

			for (BiomeManager.BiomeEntry entry : entries) {
				BiomeManager.removeBiome(type, entry);
			}
		}
	}
}
