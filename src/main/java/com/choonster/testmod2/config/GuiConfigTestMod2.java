package com.choonster.testmod2.config;

import com.choonster.testmod2.References;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.ConfigElement;

import java.util.List;
import java.util.stream.Collectors;

public class GuiConfigTestMod2 extends GuiConfig {
	public GuiConfigTestMod2(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(), References.MODID, false, false, StatCollector.translateToLocal("testmod2.config.title"));
	}

	private static List<IConfigElement> getConfigElements() {
		return Config.config.getCategoryNames().stream()
				.map(categoryName -> new ConfigElement<>(Config.config.getCategory(categoryName).setLanguageKey("testmod2.config." + categoryName)))
				.collect(Collectors.toList());
	}
}
