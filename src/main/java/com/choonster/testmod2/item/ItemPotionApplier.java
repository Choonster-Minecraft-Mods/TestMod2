package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * Applies potion effects while right click is held.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2505516-playing-an-enumaction-for-a-set-amount-of-time
 */
public class ItemPotionApplier extends Item {
	public ItemPotionApplier() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("potionApplier");
		setTextureName("diamond_sword");
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		int maxDuration = getMaxItemUseDuration(stack);

		player.setItemInUse(stack, maxDuration);
		player.addPotionEffect(new PotionEffect(Potion.invisibility.getId(), maxDuration));
		player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), maxDuration));

		return stack;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount) {
		player.removePotionEffect(Potion.invisibility.getId());
		player.removePotionEffect(Potion.moveSlowdown.getId());
	}
}
