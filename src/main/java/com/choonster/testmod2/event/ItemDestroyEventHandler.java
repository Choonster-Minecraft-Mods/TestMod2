package com.choonster.testmod2.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class ItemDestroyEventHandler {

	@SubscribeEvent
	public void onItemDestroyed(PlayerDestroyItemEvent event){
		new Exception().printStackTrace();
	}
}
