package com.choonster.testmod2.event;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class DeathListener {

	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event) {
		if (event.entity instanceof EntityPlayer) {
			EntityPlayer entityPlayer = (EntityPlayer) event.entity;
			entityPlayer.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0F);
		}

	}

}