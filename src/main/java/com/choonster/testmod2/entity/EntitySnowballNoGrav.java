package com.choonster.testmod2.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.world.World;

public class EntitySnowballNoGrav extends EntitySnowball {
	public EntitySnowballNoGrav(World world) {
		super(world);
	}

	public EntitySnowballNoGrav(World world, EntityLivingBase thrower, double motionY) {
		super(world, thrower);
		this.motionY = motionY;
		this.rotationPitch = 0;
	}

	public EntitySnowballNoGrav(World world, double posX, double posY, double posZ) {
		super(world, posX, posY, posZ);
	}

	@Override
	protected float getGravityVelocity() {
		return 0;
	}
}
