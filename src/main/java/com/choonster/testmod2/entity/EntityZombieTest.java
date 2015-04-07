package com.choonster.testmod2.entity;

import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityZombieTest extends EntityZombie {
	public EntityZombieTest(World p_i1745_1_) {
		super(p_i1745_1_);

		if (!worldObj.isRemote) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("Herobrine joined the game."));
		}
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		super.onDeath(p_70645_1_);

		if (!worldObj.isRemote) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("Herobrine left the game."));
		}
	}
}
