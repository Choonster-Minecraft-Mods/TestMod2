package com.choonster.testmod2.compat;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import com.choonster.testmod2.Logger;
import com.choonster.testmod2.References;
import com.choonster.testmod2.init.ModEntities;
import cpw.mods.fml.common.Loader;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class NEITestMod2Config implements IConfigureNEI {
	@Override
	public void loadConfig() {
		Logger.info("Initialising NEI compat");

		// Hide all vanilla spawn eggs for mod entities
		ModEntities.getModEntityGlobalIDs().stream()
				.map(id -> new ItemStack(Items.spawn_egg, 0, id))
				.forEach(API::hideItem);
	}

	@Override
	public String getName() {
		return Loader.instance().getIndexedModList().get(References.MODID).getName();
	}

	@Override
	public String getVersion() {
		return Loader.instance().getIndexedModList().get(References.MODID).getVersion();
	}
}
