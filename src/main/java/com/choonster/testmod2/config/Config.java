package com.choonster.testmod2.config;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.References;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {
	public static final String CATEGORY_BIOME = "biomes";

	public static Configuration config;

	public static float barrelBombExplosionSize;

	public static boolean disableOtherBiomes;
	public static int testBiomeID;

	public static void load(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		reloadConfig();

		FMLCommonHandler.instance().bus().register(new Config());
	}

	public static void reloadConfig() {
		barrelBombExplosionSize = config.getFloat("barrelBombExplosionSize", Configuration.CATEGORY_GENERAL, 2.0f, 0.0f, 1000.0f,
				"The size of the Barrel Bomb's explosion. TNT uses a size of 4.0, Creepers use a size of 3.0 (6.0 if charged).\nHigher numbers will cause strange explosion patterns, a lot of lag and possibly server crashes.");

		disableOtherBiomes = config.get(CATEGORY_BIOME, "disableOtherBiomes", false, "If true, disable all other biomes").setRequiresMcRestart(true).getBoolean();
		testBiomeID = getBiomeID("test", 50, "Test Biome");

		if (config.hasChanged()) {
			config.save();
		}
	}

	private static int getBiomeID(String name, int defaultValue, String comment) {
		Property property = config.get(CATEGORY_BIOME, name, defaultValue, comment, 40, 255);
		property.setRequiresMcRestart(true);
		return property.getInt();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(References.MODID)) {
			reloadConfig();
			Logger.info("Config reloaded. Explosion size: %f", barrelBombExplosionSize);
		}
	}
}
