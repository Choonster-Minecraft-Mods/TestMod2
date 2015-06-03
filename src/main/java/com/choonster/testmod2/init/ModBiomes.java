package com.choonster.testmod2.init;

import com.choonster.testmod2.biome.BiomeGenTest;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class ModBiomes {
	public static BiomeGenTest biomeGenTest;

	public static void registerBiomes(){
		biomeGenTest = new BiomeGenTest(100);
		BiomeDictionary.registerBiomeType(biomeGenTest, BiomeDictionary.Type.COLD);
		BiomeManager.addBiome(BiomeManager.BiomeType.ICY, new BiomeManager.BiomeEntry(biomeGenTest, 10));
	}
}
