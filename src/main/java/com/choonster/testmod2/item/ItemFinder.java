package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemFinder extends Item {
	public ItemFinder() {
		setCreativeTab(TestMod2.tab);
		setTextureName("minecraft:compass");
		setUnlocalizedName("finder");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			boolean found = false;

			ItemStack stackToFind = player.inventory.getStackInSlot(0);
			if (stackToFind == null || Block.getBlockFromItem(stackToFind.getItem()) == null) {
				player.addChatComponentMessage(new ChatComponentTranslation("message.finder.noBlock"));
			} else {
				Block blockToFind = Block.getBlockFromItem(stackToFind.getItem());
				int metaToFind = stackToFind.getMetadata();

				String name = stackToFind.getDisplayName();

				outerloop:
				for (int x = MathHelper.floor_double(player.posX) - 16; x <= player.posX + 16; x++) {
					for (int z = MathHelper.floor_double(player.posZ) - 16; z <= player.posZ + 16; z++) {
						for (int y = 0; y < 256; y++) {
							if (world.getBlock(x, y, z) == blockToFind && world.getBlockMetadata(x, y, z) == metaToFind) {
								player.addChatComponentMessage(new ChatComponentTranslation("message.finder.found", name, x, y, z));
								found = true;
								break outerloop;
							}
						}
					}
				}

				if (!found) {
					player.addChatComponentMessage(new ChatComponentTranslation("message.finder.notFound", name));
				}
			}


		}

		return super.onItemRightClick(stack, world, player);
	}
}
