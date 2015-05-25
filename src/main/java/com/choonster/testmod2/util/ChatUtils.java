package com.choonster.testmod2.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class ChatUtils {
	public static void sendServerMessage(String translationKey, Object... args) {
		String message = StatCollector.translateToLocalFormatted(translationKey, args);
		sendServerMessage(new ChatComponentText(message));
	}

	// Send a message to all players on the server
	public static void sendServerMessage(IChatComponent chatComponent) {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(chatComponent);
	}
}
