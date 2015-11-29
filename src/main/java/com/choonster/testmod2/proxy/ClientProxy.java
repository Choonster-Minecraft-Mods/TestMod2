package com.choonster.testmod2.proxy;

import com.choonster.testmod2.client.renderer.entity.RenderModPotion;
import com.choonster.testmod2.entity.EntityModPotion;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();

		RenderingRegistry.registerEntityRenderingHandler(EntityModPotion.class, RenderModPotion.INSTANCE);
	}
}
