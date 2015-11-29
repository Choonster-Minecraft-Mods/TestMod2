package com.choonster.testmod2.event;

import com.choonster.testmod2.util.ChatUtils;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.util.Random;
import java.util.stream.Collectors;

public class BlockEventHandler {
	private final Random random = new Random();

	@SubscribeEvent
	public void onBlockLeftClicked(PlayerInteractEvent event) {
		if (event.action != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return;

		ItemStack heldItem = event.entityPlayer.getHeldItem();
		Block block = event.world.getBlock(event.x, event.y, event.z);

		// If the block clicked was Stone, the player was holding an Iron Pickaxe and a random integer from 0 (inclusive) to 2 (exclusive) is 0 (50% chance)
		if (block == Blocks.stone && heldItem != null && heldItem.getItem() == Items.iron_pickaxe && random.nextInt(2) == 0) {
			ForgeDirection direction = ForgeDirection.getOrientation(event.face); // Convert the numeric face to a ForgeDirection
			int fireX = event.x + direction.offsetX, fireY = event.y + direction.offsetY, fireZ = event.z + direction.offsetZ; // Offset the block's coordinates according to the direction

			if (event.world.isAirBlock(fireX, fireY, fireZ)) { // If the block at the new coordinates is Air
				event.world.setBlock(fireX, fireY, fireZ, Blocks.fire); // Replace it with Fire
				event.useBlock = Event.Result.DENY; // Prevent the Fire from being extinguished (also prevents Block#onBlockClicked from being called)
			}
		}
	}

	/*@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if (event.block == Blocks.stone) {
			event.setCanceled(true);
			event.world.setBlock(event.x, event.y, event.z, Blocks.flowing_lava);
		}
	}*/

	@SubscribeEvent
	public void harvestDrops(BlockEvent.HarvestDropsEvent event) {
		if (event.block == Blocks.cocoa && event.harvester != null) {
			event.harvester.addChatComponentMessage(new ChatComponentText("Cocoa!"));
		}
	}

	@SubscribeEvent
	public void logsHarvest(BlockEvent.HarvestDropsEvent event) {
		if (event.block instanceof BlockLog) {
			// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2444501-harvestdropevent-changing-drops-of-vanilla-blocks

			ChatUtils.sendServerMessage("Logs! Harvester: %s Drops: %s", event.harvester != null ? event.harvester.getCommandSenderName() : "<none>", event.drops.stream().map(ItemStack::toString).collect(Collectors.joining(", ")));
			if (event.harvester != null) {
				ItemStack heldItem = event.harvester.getHeldItem();
				if (heldItem == null || heldItem.getItem().getHarvestLevel(heldItem, "axe") < 1) {
					event.drops.clear();
					ChatUtils.sendServerMessage("Harvester had wrong tool, clearing drops");
				} else {
					ChatUtils.sendServerMessage("Harvester had correct tool, not clearing drops");
				}
			} else {
				event.drops.clear();
				ChatUtils.sendServerMessage("No harvester, clearing drops");
			}
		}
	}
}
