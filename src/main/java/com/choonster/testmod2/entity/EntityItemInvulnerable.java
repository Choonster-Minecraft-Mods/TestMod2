package com.choonster.testmod2.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An item entity that's completely invulnerable.
 * <p>
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,38091.0.html
 *
 * @author Choonster
 */
public class EntityItemInvulnerable extends EntityItem {
	public EntityItemInvulnerable(World p_i1711_1_) {
		super(p_i1711_1_);
	}

	public EntityItemInvulnerable(World world, Entity location, ItemStack itemStack) {
		super(world, location.posX, location.posY, location.posZ, itemStack);

		copyDataFrom(location, true);

		if (location instanceof EntityItem) {
			delayBeforeCanPickup = ((EntityItem) location).delayBeforeCanPickup;
		} else {
			delayBeforeCanPickup = 10;
		}
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		isImmuneToFire = true;
	}

	@Override
	public boolean isEntityInvulnerable() {
		return true;
	}
}
