package com.choonster.testmod2.proxy;

import com.choonster.testmod2.init.ModFluids;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();

		ModFluids.setFluidIcons();
	}
}
