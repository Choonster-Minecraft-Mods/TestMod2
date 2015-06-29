package com.choonster.testmod2.entity;

import com.choonster.testmod2.util.ChatUtils;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityArmouredSkeleton extends EntitySkeleton {
	public EntityArmouredSkeleton(World world) {
		super(world);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
		ChatUtils.sendServerMessage("message.armouredSkeleton.spawn", posX, posY, posZ);

		return super.onSpawnWithEgg(p_110161_1_);
	}

	@Override
	protected void addRandomArmor() {
		super.addRandomArmor();

		setCurrentItemOrArmor(0, new ItemStack(Items.carrot_on_a_stick));

		for (int slot = 4; slot > 0; slot--){
			ItemStack stack = new ItemStack(getArmorItemForSlot(slot, 3)); // Iron Armour
			setCurrentItemOrArmor(slot, stack);
		}
	}
}
