package com.choonster.testmod2.init;

import com.choonster.testmod2.biome.BiomeGenTerrainReplacement;
import com.choonster.testmod2.biome.BiomeGenTest;
import com.choonster.testmod2.config.Config;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

import static net.minecraftforge.common.BiomeDictionary.Type.COLD;
import static net.minecraftforge.common.BiomeDictionary.Type.PLAINS;

public class ModBiomes {
	public static BiomeGenTest biomeGenTest;
	public static BiomeGenTerrainReplacement biomeGenTerrainReplacement;

	public static void registerBiomes() {
		biomeGenTest = reigsterBiome(new BiomeGenTest(Config.testBiomeID), BiomeManager.BiomeType.ICY, 10000, COLD);
		biomeGenTerrainReplacement = reigsterBiome(new BiomeGenTerrainReplacement(Config.terrainReplacementBiomeID), BiomeManager.BiomeType.COOL, 10000, PLAINS);

		if (Config.disableOtherBiomes) {
			removeOtherBiomes();
		}
	}

	private static <T extends BiomeGenBase> T reigsterBiome(T biome, BiomeManager.BiomeType biomeType, int weight, BiomeDictionary.Type... types) {
		BiomeDictionary.registerBiomeType(biome, types);
		BiomeManager.addBiome(biomeType, new BiomeManager.BiomeEntry(biome, weight));

		return biome;
	}

	/**
	 * Removes all existing entries from BiomeManager and adds a Terrain Replacement Biome entry for each biome type
	 */
	private static void removeOtherBiomes() {
		BiomeManager.BiomeEntry terrainReplacementEntry = new BiomeManager.BiomeEntry(biomeGenTerrainReplacement, 1);

		for (BiomeManager.BiomeType type : BiomeManager.BiomeType.values()) {
			for (BiomeManager.BiomeEntry entry : BiomeManager.getBiomes(type)) {
				BiomeManager.removeBiome(type, entry);
			}

			BiomeManager.addBiome(type, terrainReplacementEntry);
		}
	}
}
