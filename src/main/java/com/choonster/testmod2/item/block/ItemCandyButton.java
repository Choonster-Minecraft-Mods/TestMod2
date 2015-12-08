package com.choonster.testmod2.item.block;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.block.BlockCandyButton;
import com.choonster.testmod2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * An item that places a randomly chosen Candy Button.
 */
public class ItemCandyButton extends ItemBlock {

	public ItemCandyButton(Block block) {
		super(block);
		setCreativeTab(TestMod2.tab);
	}

	private BlockCandyButton getCandyButtonForDisplay(ItemStack stack) {
		return ModBlocks.candyButtons[stack.getMetadata() - 1];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.getMetadata() > 0) { // Metadata values > 0 are used for Waila displays
			return getCandyButtonForDisplay(stack).getUnlocalizedName();
		} else {
			return "item.candyButton";
		}
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		if (stack.getMetadata() > 0) { // Metadata values > 0 are used for Waila displays
			return getCandyButtonForDisplay(stack).getIcon(1, 0);
		} else {
			return super.getIcon(stack, pass);
		}
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
