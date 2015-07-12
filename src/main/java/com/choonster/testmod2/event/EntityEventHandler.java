package com.choonster.testmod2.event;

import com.choonster.testmod2.util.ChatUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class EntityEventHandler {
	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event){
		if (event.entity instanceof ICommandSender && !event.world.isRemote){
			ChatUtils.sendServerMessage("§0%s has joined!§r § ", event.entity.getCommandSenderName());
		}
	}
}
