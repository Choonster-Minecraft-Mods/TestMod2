package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntityBarrelBomb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2433859-help-forge-modding-worldobj-createexplosion-not
public class ItemBarrelBomb extends Item {
	public ItemBarrelBomb() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("barrelbomb");
		setTextureName("fireworks");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) {
			--itemStack.stackSize;
		}

		world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		if (!world.isRemote) {
			world.spawnEntityInWorld(new EntityBarrelBomb(world, player));
		}

		return itemStack;
	}
}
