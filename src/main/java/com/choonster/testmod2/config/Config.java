package com.choonster.testmod2.config;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.References;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;

public class Config {
	public static Configuration config;

	public static float barrelBombExplosionSize;

	public static void load(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		reloadConfig();

		FMLCommonHandler.instance().bus().register(new Config());
	}

	public static void reloadConfig() {
		barrelBombExplosionSize = config.getFloat("barrelBombExplosionSize", Configuration.CATEGORY_GENERAL, 2.0f, 0.0f, 1000.0f,
				"The size of the Barrel Bomb's explosion. TNT uses a size of 4.0, Creepers use a size of 3.0 (6.0 if charged).\nHigher numbers will cause strange explosion patterns, a lot of lag and possibly server crashes.");

		if (config.hasChanged()) {
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equals(References.MODID)) {
			reloadConfig();
			Logger.info("Config reloaded. Explosion size: %f", barrelBombExplosionSize);
		}
	}
}
