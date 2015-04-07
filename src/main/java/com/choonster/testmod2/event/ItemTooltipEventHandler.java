package com.choonster.testmod2.event;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemTooltipEventHandler {

	public static void init() {
		//MinecraftForge.EVENT_BUS.register(new ItemTooltipEventHandler());


	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onItemTooltip(ItemTooltipEvent event) {
		event.toolTip.clear();
	}
}
