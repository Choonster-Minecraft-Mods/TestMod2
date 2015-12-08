package com.choonster.testmod2.compat.waila;

import com.choonster.testmod2.block.BlockCandyButton;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaCompat {
	public static void register(IWailaRegistrar registrar) {
		registrar.registerStackProvider(new HUDHandlerCandyButton(), BlockCandyButton.class);
	}
}
