package com.choonster.testmod2.item.block;

import com.choonster.testmod2.init.ModBlocks;
import com.choonster.testmod2.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCandyButton extends ItemBlock {
	public ItemCandyButton(Block block, Integer colourIndex) {
		super(block);

		if (colourIndex == 0) {
			ModItems.candyButton = this;
		}
	}

	// Override this so we can return the generic "Candy Button" like a normal item instead of the block's "Red Candy Button"
	@Override
	public String getUnlocalizedName() {
		return "item.candyButton";
	}

	@Override
	public String getUnlocalizedName(ItemStack p_77667_1_) {
		return getUnlocalizedName();
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		// Randomly choose a Candy Button to place
		Block blockCandyButton = ModBlocks.candyButtons[itemRand.nextInt(ModBlocks.candyButtons.length)];

		if (!world.setBlock(x, y, z, blockCandyButton, metadata, 3)) {
			return false;
		}

		if (world.getBlock(x, y, z) == blockCandyButton) {
			blockCandyButton.onBlockPlacedBy(world, x, y, z, player, stack);
			blockCandyButton.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}
}
