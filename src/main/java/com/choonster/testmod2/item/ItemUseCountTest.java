package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.util.StatUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.stats.StatList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemUseCountTest extends Item {
	public ItemUseCountTest() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("useCountTest");
		setTextureName("comparator");
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) { // If this is the server and the player is sneaking,
			StatUtils.forceStatsUpdate((EntityPlayerMP) player);
			player.addChatComponentMessage(new ChatComponentTranslation("message.useCountTest.updateSent"));
		}

		return super.onItemRightClick(stack, world, player);
	}

	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("unchecked")
	public void addInformation(ItemStack stack, EntityPlayer player, List tooltipLines, boolean advancedTooltips) {
		StatFileWriter stats = ((EntityClientPlayerMP) player).getStatFileWriter();

		StatBase useCountStat = StatList.objectUseStats[Item.getIdFromItem(this)];
		int useCount = stats.writeStat(useCountStat);

		tooltipLines.add(StatCollector.translateToLocalFormatted("tooltip.useCountTest.count", useCount));
		tooltipLines.add(StatCollector.translateToLocal("tooltip.useCountTest.update"));
	}
}
