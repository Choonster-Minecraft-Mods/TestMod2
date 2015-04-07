package com.choonster.testmod2.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.world.World;

public class EntitySuperTNTPrimed extends EntityTNTPrimed {
	public EntitySuperTNTPrimed(World world, double x, double y, double z, EntityLivingBase tntPlacedBy) {
		super(world, x, y, z, tntPlacedBy);
	}

	public EntitySuperTNTPrimed(World world) {
		super(world);
	}

	// Exactly the same code as EntityTNTPrimed#onUpdate, but overriding it here calls this class's explode method
	// instead of EntityTNTPrimed#explode (which can't be overridden since it's private)
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.motionY -= 0.03999999910593033D;
		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
			this.motionY *= -0.5D;
		}

		if (this.fuse-- <= 0) {
			this.setDead();

			if (!this.worldObj.isRemote) {
				this.explode();
			}
		} else {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
		}
	}

	// Create the Explosion. Normal TNT uses force 4, we use force 100
	// EntityPrimedTNT#explode is private, so we can't override it
	private void explode() {
		this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, 100.0F, true);
	}
}
