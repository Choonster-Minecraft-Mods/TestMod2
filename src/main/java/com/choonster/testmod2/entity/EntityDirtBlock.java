package com.choonster.testmod2.entity;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDirtBlock extends EntityThrowable {

	public EntityDirtBlock(World world) {
		super(world);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}


	@Override
	protected void onImpact(MovingObjectPosition movObjPos) {
		// If it didn't hit the top of a block, do nothing
		if (movObjPos.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK || movObjPos.sideHit != 1)
			return;

		// Build a wall of Dirt 13x2x1 (X Y Z) one block above and horizontally centred on the impact block

		int posY = movObjPos.blockY + 1;
		int posZ = movObjPos.blockZ;

		for (int x = -6; x <= 6; x++) {
			int posX = movObjPos.blockX + x;
			worldObj.setBlock(posX, posY, posZ, Blocks.dirt);
			worldObj.setBlock(posX, posY + 1, posZ, Blocks.dirt);
		}
	}

}