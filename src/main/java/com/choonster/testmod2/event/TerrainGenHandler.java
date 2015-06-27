package com.choonster.testmod2.event;

import com.choonster.testmod2.worldgen.MapGenIronBlockCaves;
import com.choonster.testmod2.worldgen.MapGenWrapper;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;

public class TerrainGenHandler {

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void initMapGen(InitMapGenEvent event) {
		if (event.type == InitMapGenEvent.EventType.CAVE) {
			event.newGen = new MapGenWrapper(event.newGen, new MapGenIronBlockCaves());
		}
	}
}
