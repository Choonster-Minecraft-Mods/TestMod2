package com.choonster.testmod2.block;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * A Block that prints the current state of the player's held ItemStack on the client and server when right clicked.
 */
public class BlockItemDebugger extends Block {
	public BlockItemDebugger() {
		super(Material.iron);
		setCreativeTab(TestMod2.tab);
		setBlockName("itemDebugger");
		setBlockTextureName("minecraft:redstone_lamp_off");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem();
		if (stack != null) {
			Logger.info("Item: %s", stack);
			if (stack.hasTagCompound()) {
				Logger.info("NBT data: %s", stack.getTagCompound());
			}
		}

		return true;
	}
}
