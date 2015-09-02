package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

/**
 * A block that applies a potion effect to any entity that collides with it.
 *
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2516174-blocks-give-potion-effects
 */
public class BlockCollisionPotionEffects extends Block {
	public BlockCollisionPotionEffects() {
		super(Material.rock);
		setCreativeTab(TestMod2.tab);
		setBlockName("collisionPotionEffects");
		setBlockTextureName("minecraft:diamond_block");
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityLivingBase && !world.isRemote) {
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 60, 1));
		}
	}
}
