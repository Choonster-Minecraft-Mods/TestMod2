package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntityItemCollisionTest;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCollisionTest extends Item {
	public ItemCollisionTest() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("collisiontestitem");
		setTextureName("brick");
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack) {
		return true;
	}

	@Override
	public Entity createEntity(World world, Entity location, ItemStack itemstack) {
		return new EntityItemCollisionTest(world, location, itemstack);
	}
}
