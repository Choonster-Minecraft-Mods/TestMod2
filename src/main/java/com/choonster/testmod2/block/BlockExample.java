package com.choonster.testmod2.block;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockExample extends Block {
	// A small value to offset each side of the block's bounding box by to allow entities to collide with the block
	// and thus call onEntityCollidedWithBlock
	private static final float ENTITY_COLLISION_MIN = 0.01f;

	public BlockExample() {
		super(Material.rock);
		setCreativeTab(TestMod2.tab);
		setBlockName("exampleblock");
		setBlockBounds();
	}

	private void setBlockBounds() {
		float min = ENTITY_COLLISION_MIN;
		float max = 1 - min;
		setBlockBounds(min, min, min, max, max, max);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		super.onEntityCollidedWithBlock(world, x, y, z, entity);

		entity.attackEntityFrom(DamageSource.cactus, 1);
		Logger.info("collide %d,%d,%d - %s", x, y, z, entity);
	}
}
