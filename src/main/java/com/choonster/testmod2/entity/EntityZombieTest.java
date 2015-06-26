package com.choonster.testmod2.entity;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityZombieTest extends EntityZombie {
	public EntityZombieTest(World p_i1745_1_) {
		super(p_i1745_1_);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData livingData) {
		if (!worldObj.isRemote) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("Herobrine joined the game."));
		}

		return super.onSpawnWithEgg(livingData);
	}

	@Override
	public void onDeath(DamageSource damageSource) {
		super.onDeath(damageSource);

		if (!worldObj.isRemote) {
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText("Herobrine left the game."));
		}
	}
}
