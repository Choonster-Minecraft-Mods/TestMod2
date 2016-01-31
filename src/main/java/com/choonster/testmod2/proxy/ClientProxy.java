package com.choonster.testmod2.proxy;

import com.choonster.testmod2.client.command.CommandConnectToServer;
import com.choonster.testmod2.client.renderer.entity.RenderModPotion;
import com.choonster.testmod2.entity.EntityModPotion;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraftforge.client.ClientCommandHandler;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();

		RenderingRegistry.registerEntityRenderingHandler(EntityModPotion.class, RenderModPotion.INSTANCE);
		ClientCommandHandler.instance.registerCommand(new CommandConnectToServer());
	}
}
