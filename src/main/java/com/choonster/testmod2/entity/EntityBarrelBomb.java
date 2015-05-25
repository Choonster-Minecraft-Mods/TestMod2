package com.choonster.testmod2.entity;

import com.choonster.testmod2.config.Config;
import com.choonster.testmod2.util.ChatUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2433859-help-forge-modding-worldobj-createexplosion-not
public class EntityBarrelBomb extends EntityThrowable {
	private ICommandSender entityCommandSender;

	public EntityBarrelBomb(World p_i1776_1_) {
		super(p_i1776_1_);
	}

	public EntityBarrelBomb(World world, EntityLivingBase entity) {
		super(world, entity);
	}

	private String getThrowerName() {
		EntityLivingBase thrower = getThrower();
		if (thrower != null) {
			return thrower.getCommandSenderName();
		} else {
			return "<unknown>";
		}
	}

	@Override
	public void onImpact(MovingObjectPosition p_70184_1_) {
		for (int i = 0; i < 10; i++) {
			this.worldObj.spawnParticle("largesmoke", this.posX, this.posY, this.posZ, 0f, 0f, 0f);
		}

		if (!this.worldObj.isRemote) {
			this.setDead();

			ChatUtils.sendServerMessage("Barrel Bomb explosion started by %s! Prepare for lag!", getThrowerName());

			long time = System.nanoTime();
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, Config.barrelBombExplosionSize, true);
			long diff = System.nanoTime() - time;

			ChatUtils.sendServerMessage("Explosion of size %f took %.2f seconds", Config.barrelBombExplosionSize, (double) diff / 1e9d);
		}
	}
}
