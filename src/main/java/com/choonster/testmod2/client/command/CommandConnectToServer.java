package com.choonster.testmod2.client.command;

import com.choonster.testmod2.Logger;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentTranslation;

import java.util.Optional;

/**
 * A client command that connects the player to another server.
 * <p>
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,36215.0.html
 *
 * @author Choonster
 */
@SideOnly(Side.CLIENT)
public class CommandConnectToServer extends CommandBase {
	/**
	 * The pending server connection, if any.
	 */
	private Optional<ServerData> pendingConnection = Optional.empty();

	/**
	 * The {@link Minecraft} instance
	 */
	private final Minecraft minecraft = Minecraft.getMinecraft();

	public CommandConnectToServer() {
		FMLCommonHandler.instance().bus().register(this);
	}

	@Override
	public String getCommandName() {
		return "connect";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "commands.testmod2:connect.usage";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}

	/**
	 * Add a pending connection to the specified server then disconnect from the current server.
	 *
	 * @param sender The command sender
	 * @param args   The command arguments
	 */
	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if (args.length == 1) {
			String serverIP = args[0];

			sender.addChatMessage(new ChatComponentTranslation("commands.testmod2:connect.connecting", serverIP));

			// Add the pending connection
			pendingConnection = Optional.of(new ServerData("Connect Command", serverIP));

			// Disconnect from the current server
			minecraft.theWorld.sendQuittingDisconnectingPacket();
			minecraft.loadWorld(null);
			minecraft.displayGuiScreen(new GuiMainMenu());
		} else {
			throw new WrongUsageException("commands.testmod2:connect.usage");
		}
	}

	/**
	 * When the client disconnects from a server with a pending connection, connect to the new server.
	 * <p>
	 * This is fired on a Netty thread, so it's not safe to directly interact with Minecraft classes in this handler.
	 * A task must be scheduled to run on the main thread using {@link Minecraft#addScheduledTask}.
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		minecraft.addScheduledTask(() -> {
			if (pendingConnection.isPresent()) {
				final ServerData serverData = pendingConnection.get();
				Logger.info("Connecting to %s", serverData.serverIP);

				FMLClientHandler.instance().setupServerList();
				FMLClientHandler.instance().connectToServer(new GuiMainMenu(), serverData);
				pendingConnection = Optional.empty();
			}
		});
	}
}
