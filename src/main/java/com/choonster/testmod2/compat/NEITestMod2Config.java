package com.choonster.testmod2.compat;

import codechicken.nei.api.IConfigureNEI;
import com.choonster.testmod2.Logger;
import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import cpw.mods.fml.common.Loader;

public class NEITestMod2Config implements IConfigureNEI {
	@Override
	public void loadConfig() {
		Logger.info("Initialising NEI compat");
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
