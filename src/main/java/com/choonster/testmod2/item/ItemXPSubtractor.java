package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

/**
 * Subtracts XP from the player when used.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2503178-remove-xp-from-player-forge
 */
public class ItemXPSubtractor extends Item {
	public ItemXPSubtractor() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("xpSubtractor");
		setTextureName("flint");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.isSneaking()) {
			player.addExperienceLevel(-1);
		} else {
			player.addExperience(-100); // Doesn't work
		}

		return super.onItemRightClick(stack, world, player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltipLines, boolean advancedTooltips) {
		tooltipLines.add(StatCollector.translateToLocal("item.xpSubtractor.tooltip"));
	}
}
