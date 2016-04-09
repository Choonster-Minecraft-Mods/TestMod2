package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntityItemInvulnerable;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * An item that's completely invulnerable and has a lifespan of {@link Integer#MAX_VALUE} ticks (3.4 years) while in entity form.
 * <p>
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,38091.0.html
 *
 * @author Choonster
 */
public class ItemInvulnerable extends Item {
	public ItemInvulnerable() {
		setCreativeTab(TestMod2.tab);
		setTextureName("minecraft:nether_star");
		setUnlocalizedName("testmod2:invulnerable");
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EntityItemInvulnerable(world, location, itemstack);
	}

	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return Integer.MAX_VALUE;
	}
}
