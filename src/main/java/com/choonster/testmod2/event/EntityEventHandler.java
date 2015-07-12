package com.choonster.testmod2.event;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.util.ChatUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class EntityEventHandler {
	@SubscribeEvent
	public void entityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof ICommandSender && !event.world.isRemote) {
			ChatUtils.sendServerMessage("§0%s has joined!§r § ", event.entity.getCommandSenderName());
		}
	}

	/**
	 * Creates an EntityItem at the Entity's position with the ItemStack.
	 * <p>
	 * Similar to Entity#entityDropItem but doesn't spawn the EntityItem in the World.
	 *
	 * @param entity The Entity
	 * @param stack  The ItemStack
	 * @return The EntityItem
	 */
	private static EntityItem dropItem(Entity entity, ItemStack stack) {
		EntityItem entityItem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, stack);
		entityItem.delayBeforeCanPickup = 10;
		return entityItem;
	}

	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event) {
		if (event.entityLiving instanceof EntityPig && event.source instanceof EntityDamageSource) {
			// getEntity will return the Entity that caused the damage, even for indirect damage sources like arrows/fireballs
			// (where it will return the Entity that shot the projectile rather than the projectile itself)
			Entity sourceEntity = event.source.getEntity();
			ItemStack heldItem = sourceEntity instanceof EntityLivingBase ? ((EntityLivingBase) sourceEntity).getHeldItem() : null;

			if (heldItem != null && heldItem.getItem() == Items.iron_pickaxe) {
				Logger.info("EntityPig drops event");
				event.drops.clear();
				dropItem(event.entity, new ItemStack(Items.diamond, 64));
			}
		}
	}
}
