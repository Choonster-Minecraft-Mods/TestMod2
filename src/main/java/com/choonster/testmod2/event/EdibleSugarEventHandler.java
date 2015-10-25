package com.choonster.testmod2.event;

import com.choonster.testmod2.Logger;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

/**
 * Makes Sugar edible.
 * <p>
 * NOT CURRENTLY FUNCTIONAL:
 * When a player right clicks, the client sends a {@link C08PacketPlayerBlockPlacement} to the server.
 * When the server handles this in {@link NetHandlerPlayServer#processPlayerBlockPlacement},
 * it checks if the player's held item is null or if its maximum use duration is 0 (which is the case for Sugar) and
 * then sets the player's held {@link ItemStack} to a copy of the held {@link ItemStack}
 * (i.e. it creates a new {@link ItemStack} object with the same contents).
 * <p>
 * {@link EntityPlayer#onUpdate} then checks if the {@link ItemStack} currently in use is the same object as the
 * player's held {@link ItemStack} and if it isn't, stops using the item.
 * Since the packet handler replaced the player's held item with a new {@link ItemStack} object,
 * the player stops using the item before {@link PlayerUseItemEvent.Finish} is fired.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2468534-modifying-vinilla-item-help?comment=16
 */
public class EdibleSugarEventHandler {
	public static final boolean SUGAR_IS_ALWAYS_EDIBLE = true;
	public static final int SUGAR_USE_DURATION = 32;

	public static final int SUGAR_FOOD_LEVEL = 0;
	public static final float SUGAR_SATURATION_LEVEL = 0.0f;

	public static final int SUGAR_SPEED_DURATION = 30 * 20;
	public static final int SUGAR_SPEED_AMPLIFIER = 0;


	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		ItemStack heldItem = event.entityPlayer.getHeldItem();
		if (!event.entityPlayer.worldObj.isRemote) {
			Logger.info("Player right click: %s - %s - %s", heldItem, event.action, event.entityPlayer.canEat(SUGAR_IS_ALWAYS_EDIBLE));
		}

		if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR && heldItem != null && heldItem.getItem() == Items.sugar) {
			if (event.entityPlayer.canEat(SUGAR_IS_ALWAYS_EDIBLE)) {
				event.entityPlayer.setItemInUse(heldItem, SUGAR_USE_DURATION);

				// EntityPlayerMP#setItemInUse sends a S0BPacketAnimation with a type of 3 when the item returns EnumAction.eat from Item#getItemUseAction,
				// but the client never actually does anything for that type so we don't bother sending it here.
			}
		}
	}

	/**
	 * Adapted from {@link EntityPlayer#updateItemUse(ItemStack, int)}, spawns particles and plays sounds while Sugar is being eaten.
	 *
	 * @param player       The player
	 * @param itemStack    The Sugar ItemStack
	 * @param numParticles The number of particles to spawn
	 */
	private void spawnParticles(EntityPlayer player, ItemStack itemStack, int numParticles) {
		for (int j = 0; j < numParticles; ++j) {
			Vec3 velocity = Vec3.createVectorHelper((player.getRNG().nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
			velocity.rotateAroundX(-player.rotationPitch * (float) Math.PI / 180.0f);
			velocity.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0f);

			Vec3 position = Vec3.createVectorHelper((player.getRNG().nextFloat() - 0.5) * 0.3, (-player.getRNG().nextFloat()) * 0.6 - 0.3, 0.6);
			position.rotateAroundX(-player.rotationPitch * (float) Math.PI / 180.0f);
			position.rotateAroundY(-player.rotationYaw * (float) Math.PI / 180.0f);
			position = position.addVector(player.posX, player.posY + player.getEyeHeight(), player.posZ);

			String particleName = "iconcrack_" + Item.getIdFromItem(itemStack.getItem());
			if (itemStack.getHasSubtypes()) {
				particleName = particleName + "_" + itemStack.getItemDamage();
			}

			player.worldObj.spawnParticle(particleName, position.xCoord, position.yCoord, position.zCoord, velocity.xCoord, velocity.yCoord + 0.05D, velocity.zCoord);
		}

		player.playSound("random.eat", 0.5f + 0.5f * player.getRNG().nextInt(2), (player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.2f + 1.0f);
	}

	@SubscribeEvent
	public void onItemUseTick(PlayerUseItemEvent.Tick event) {
		if (event.duration <= 25 && event.duration % 4 == 0) {
			spawnParticles(event.entityPlayer, event.item, 5);
		}
	}

	@SubscribeEvent
	public void onItemUseFinish(PlayerUseItemEvent.Finish event) {
		if (!event.entityPlayer.worldObj.isRemote) {
			Logger.info("Player use item finish: %s", event.item);
		}

		if (event.item.getItem() == Items.sugar) {
			EntityPlayer player = event.entityPlayer;
			World world = player.worldObj;

			spawnParticles(player, event.item, 16);

			event.item.stackSize--;
			player.getFoodStats().addStats(SUGAR_FOOD_LEVEL, SUGAR_SATURATION_LEVEL);
			world.playSoundAtEntity(player, "random.burp", 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
			if (!world.isRemote) {
				Logger.info("Applying potion effect");
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), SUGAR_SPEED_DURATION, SUGAR_SPEED_AMPLIFIER));
			}
		}
	}
}
