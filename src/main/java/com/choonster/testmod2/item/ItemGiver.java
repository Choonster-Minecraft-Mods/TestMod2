package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

// Item that gives the player another Item when used
// Test for this thread: http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2454483-buggy-item-given-onrightclick
public class ItemGiver extends Item {
	public ItemGiver() {
		setCreativeTab(TestMod2.tab);
		setTextureName("minecraft:clock");
		setUnlocalizedName("giver");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltipLines, boolean advancedTooltips) {
		super.addInformation(stack, player, tooltipLines, advancedTooltips);

		tooltipLines.add(StatCollector.translateToLocal("item.giver.tooltip"));
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		ItemStack stackToGive = new ItemStack(Items.clay_ball);
		if (!player.inventory.addItemStackToInventory(stackToGive)) {
			player.dropPlayerItemWithRandomChoice(stackToGive, false);
		}

		if (!world.isRemote) {
			player.addChatComponentMessage(new ChatComponentTranslation("message.giver.give"));
		}

		return super.onItemRightClick(stack, world, player);
	}
}
