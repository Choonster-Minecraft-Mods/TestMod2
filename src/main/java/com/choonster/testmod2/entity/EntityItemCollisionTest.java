package com.choonster.testmod2.entity;

import com.choonster.testmod2.Logger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class EntityItemCollisionTest extends EntityItem {
	public EntityItemCollisionTest(World world, Entity original, ItemStack stack) {
		super(world, original.posX, original.posY, original.posZ);

		delayBeforeCanPickup = 20;
		motionX = original.motionX;
		motionY = original.motionY;
		motionZ = original.motionZ;
		setEntityItemStack(stack);
	}

	public EntityItemCollisionTest(World p_i1711_1_) {
		super(p_i1711_1_);
	}

	@Override
	public void moveEntity(double x, double y, double z) {
		if (!worldObj.isRemote) {

			List list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(x, y, z));

			Logger.debug("moveEntity %s - %d,%d,%d - %d", this, MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z), list.size());

			for (int i = 0; i < list.size(); i++) {
				Logger.debug("%s", list.get(i));
			}
		}

		super.moveEntity(x, y, z);
	}
}
