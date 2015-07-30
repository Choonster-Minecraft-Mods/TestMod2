package com.choonster.testmod2.entity;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.world.World;

public class EntityModChicken extends EntityChicken {
	public EntityModChicken(World world) {
		super(world);
	}

	@Override
	public EntityModChicken createChild(EntityAgeable p_90011_1_) {
		return new EntityModChicken(this.worldObj);
	}
}
