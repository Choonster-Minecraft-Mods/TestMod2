package com.choonster.testmod2.init;


import com.choonster.testmod2.event.TerrainGenHandler;
import com.choonster.testmod2.world.gen.structure.MapGenScatteredFeatureModBiomes;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.MinecraftForge;

public class ModMapGen {
	public static void registerMapGen() {
		MinecraftForge.TERRAIN_GEN_BUS.register(new TerrainGenHandler());
	}
}

