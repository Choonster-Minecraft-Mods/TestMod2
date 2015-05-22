package com.choonster.testmod2;

import com.choonster.testmod2.crafting.CraftingManager;
import com.choonster.testmod2.creativetab.CreativeTabTestMod2;
import com.choonster.testmod2.event.BlockEventHandler;
import com.choonster.testmod2.event.ItemDestroyEventHandler;
import com.choonster.testmod2.init.BlockRegistry;
import com.choonster.testmod2.init.Entities;
import com.choonster.testmod2.init.ItemRegistry;
import com.choonster.testmod2.tweak.unpunchablelogs.UnpunchableLogs;
import com.choonster.testmod2.util.SplitterTest;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(name = "Test Mod 2", modid = References.MODID)
public class TestMod2 {
	@Instance
	public static TestMod2 instance;

	public static CreativeTabs tab;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		tab = new CreativeTabTestMod2();

		BlockRegistry.registerBlocks();
		ItemRegistry.registerItems();
		Entities.preInit();

		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
		MinecraftForge.EVENT_BUS.register(new ItemDestroyEventHandler());
		//ItemTooltipEventHandler.init();

		SplitterTest.splitStrings();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		GameRegistry.addRecipe(new ItemStack(Blocks.diamond_block), "PP", "PP", 'P', Blocks.planks);
		GameRegistry.addRecipe(new ItemStack(Blocks.diamond_block), "SSS", 'S', new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE));

		CraftingManager.mainRegistry();

		UnpunchableLogs.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
