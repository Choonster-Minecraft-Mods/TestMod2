package com.choonster.testmod2.item;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * An Item that prints the current state of a Block and its TileEntity on the client and server when right clicked.
 */
public class ItemBlockDebugger extends Item {
	public ItemBlockDebugger() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("blockDebugger");
		setTextureName("minecraft:nether_star");
	}

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block block = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		Logger.info("Block at %d,%d,%d: %s (meta %d) - Registry name: %s", x, y, z, block, meta, Block.blockRegistry.getNameForObject(block));
		if (tileEntity != null) {
			NBTTagCompound tagCompound = new NBTTagCompound();
			tileEntity.writeToNBT(tagCompound);
			Logger.info("TileEntity data: %s", tagCompound);
		}

		return true;
	}
}
