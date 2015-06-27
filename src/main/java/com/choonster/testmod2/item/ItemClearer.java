package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemClearer extends Item {
	private static ImmutableList<Block> whitelist = ImmutableList.of(Blocks.stone, Blocks.dirt, Blocks.grass, Blocks.gravel, Blocks.sand, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava);

	public ItemClearer() {
		setCreativeTab(TestMod2.tab);
		setTextureName("minecraft:nether_star");
		setUnlocalizedName("clearer");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			int minX = MathHelper.floor_double(player.posX / 16) * 16;
			int minZ = MathHelper.floor_double(player.posZ / 16) * 16;

			player.addChatComponentMessage(new ChatComponentTranslation("message.clearer.clearing", minX, minZ));

			for (int x = minX; x < minX + 16; x++) {
				for (int z = minZ; z < minZ + 16; z++) {
					for (int y = 0; y < 256; y++) {
						if (whitelist.contains(world.getBlock(x, y, z))) {
							world.setBlockToAir(x, y, z);
						}
					}
				}
			}

			player.addChatComponentMessage(new ChatComponentTranslation("message.clearer.cleared"));
		}

		return super.onItemRightClick(stack, world, player);
	}
}
