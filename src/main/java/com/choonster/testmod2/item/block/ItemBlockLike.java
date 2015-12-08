package com.choonster.testmod2.item.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * An abstract class that imitates {@link ItemBlock} for {@link Block}s that don't have a regular {@link Item} form (e.g. String, Sugar Cane, Cake).
 */
public abstract class ItemBlockLike extends Item {

	/**
	 * Get the {@link Block} placed by the {@link ItemStack}.
	 *
	 * @param itemStack The {@link ItemStack}
	 * @return The {@link Block}
	 */
	public abstract Block getBlock(ItemStack itemStack);

	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		Block blockToPlace = getBlock(itemStack);

		Block clickedBlock = world.getBlock(x, y, z);

		if (clickedBlock == Blocks.snow_layer && (world.getBlockMetadata(x, y, z) & 7) < 1) {
			side = 1;
		} else if (clickedBlock != Blocks.vine && clickedBlock != Blocks.tallgrass && clickedBlock != Blocks.deadbush && !clickedBlock.isReplaceable(world, x, y, z)) {
			ForgeDirection clickedSide = ForgeDirection.getOrientation(side);
			x += clickedSide.offsetX;
			y += clickedSide.offsetY;
			z += clickedSide.offsetZ;
		}

		if (itemStack.stackSize == 0) {
			return false;
		} else if (!player.canPlayerEdit(x, y, z, side, itemStack)) {
			return false;
		} else if (y == 255 && blockToPlace.getMaterial().isSolid()) {
			return false;
		} else if (world.canPlaceEntityOnSide(blockToPlace, x, y, z, false, side, player, itemStack)) {
			int blockMeta = this.getMetadata(itemStack.getMetadata());
			blockMeta = blockToPlace.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, blockMeta);

			if (placeBlockAt(blockToPlace, itemStack, player, world, x, y, z, side, hitX, hitY, hitZ, blockMeta)) {
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, blockToPlace.stepSound.getPlaceSound(), (blockToPlace.stepSound.getVolume() + 1.0F) / 2.0F, blockToPlace.stepSound.getFrequency() * 0.8F);
				--itemStack.stackSize;
			}

			return true;
		} else {
			return false;
		}
	}

	/**
	 * Called to actually place the {@link Block}, after the location is determined and all permission checks have been made.
	 *
	 * @param stack  The {@link ItemStack} that was used to place the {@link Block}. This can be changed inside the method.
	 * @param player The player who is placing the {@link Block}. Can be {@code null} if the {@link Block} is not being placed by a player.
	 * @param side   The side the player (or machine) right-clicked on.
	 */
	public boolean placeBlockAt(Block blockToPlace, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (!world.setBlock(x, y, z, blockToPlace, metadata, 3)) {
			return false;
		}

		if (world.getBlock(x, y, z) == blockToPlace) {
			blockToPlace.onBlockPlacedBy(world, x, y, z, player, stack);
			blockToPlace.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}
}
