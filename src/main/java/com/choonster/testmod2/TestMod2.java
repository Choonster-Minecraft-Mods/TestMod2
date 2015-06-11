package com.choonster.testmod2;

import com.choonster.testmod2.compat.BCCompat;
import com.choonster.testmod2.config.Config;
import com.choonster.testmod2.crafting.CraftingManager;
import com.choonster.testmod2.creativetab.CreativeTabTestMod2;
import com.choonster.testmod2.event.BlockEventHandler;
import com.choonster.testmod2.event.ItemDestroyEventHandler;
import com.choonster.testmod2.init.*;
import com.choonster.testmod2.proxy.CommonProxy;
import com.choonster.testmod2.util.SplitterTest;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
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

@Mod(name = "Test Mod 2", modid = References.MODID, guiFactory = "com.choonster.testmod2.config.GuiConfigFactoryTestMod2")
public class TestMod2 {
	@Instance
	public static TestMod2 instance;

	public static CreativeTabs tab;

	@SidedProxy(clientSide = "com.choonster.testmod2.proxy.ClientProxy", serverSide = "com.choonster.testmod2.proxy.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		tab = new CreativeTabTestMod2();

		Config.load(event);

		ModFluids.registerFluids();
		BlockRegistry.registerBlocks();
		ItemRegistry.registerItems();
		Entities.preInit();
		ModBiomes.registerBiomes();

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

		//UnpunchableLogs.init();
		BCCompat.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
