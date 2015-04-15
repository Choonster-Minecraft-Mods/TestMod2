package com.choonster.testmod2.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

public class ChatUtils {
	// Send a message to all players on the server
	public static void sendServerMessage(IChatComponent chatComponent) {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(chatComponent);
	}
}
