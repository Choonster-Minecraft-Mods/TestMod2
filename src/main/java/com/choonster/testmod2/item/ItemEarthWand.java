package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2411998-checking-items-mode-before-using-it
public class ItemEarthWand extends Item {
	public static final int NUM_MODES = 3;

	public ItemEarthWand() {
		setMaxDamage(2000);
		setUnlocalizedName("earthWand"); // Set the base unlocalised name

		// Replace these with the appropriate creative tab and texture
		setCreativeTab(TestMod2.tab);
		setTextureName("minecraft:stick");
	}

	// Get the current mode of the ItemStack
	private int getMode(ItemStack stack) {
		if (!stack.hasTagCompound()) { // Ensure the ItemStack has an NBTTagCompound with a "mode" tag
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setInteger("mode", 0);
			stack.setTagCompound(tagCompound);
		}

		return stack.getTagCompound().getInteger("mode") % NUM_MODES; // Ensure mode is < NUM_MODES
	}

	// Cycle the ItemStack to the next mode
	private void cycleMode(ItemStack stack) {
		int mode = (getMode(stack) + 1) % NUM_MODES; // Increment the mode, but wrap back to 0 when the current mode is 2
		stack.getTagCompound().setInteger("mode", mode);
	}

	// Called when the player right clicks a block
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote)
			return false; // If we're not on the server, do nothing (the server will sync any changes with the client)

		if (player.isSneaking()) {
			cycleMode(stack);
			return true;
		} else {
			Block block;

			switch (getMode(stack)) {
				case 0:
					block = Blocks.dirt;
					break;
				case 1:
					block = Blocks.cobblestone;
					break;
				case 2:
					block = Blocks.stone;
					break;
				default:
					return false; // Invalid mode, do nothing
			}


			if (world.getBlock(x, y, z) != Blocks.snow_layer) {
				if (side == 0) --y; // This is equal to ForgeDirection.DOWN
				if (side == 1) ++y; // This is equal to ForgeDirection.UP
				if (side == 2) --z; // This is equal to ForgeDirection.NORTH
				if (side == 3) ++z; // This is equal to ForgeDirection.SOUTH
				if (side == 4) --x; // This is equal to ForgeDirection.WEST
				if (side == 5) ++x; // This is equal to ForgeDirection.EAST

				if (!world.isAirBlock(x, y, z)) return false;
			}

			if (!player.canPlayerEdit(x, y, z, side, stack)) { // Make sure it's not Adventure mode
				return false;
			} else if (block.canPlaceBlockAt(world, x, y, z)) { // This makes sure the block can be placed here
				stack.damageItem(5, player);
				world.setBlock(x, y, z, block);
				return true;
			}
		}

		return false;
	}

	// Called when the player right clicks air
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote && player.isSneaking()) { // If we're on the server and the player is sneaking, cycle the mode
			cycleMode(stack);
		}

		return stack;
	}

	// Allows the name to change with the tier of the wand
	// e.g. item.earthWand.tier.0.name=Earth Wand (Tier 1)
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return String.format("%s.tier.%d", getUnlocalizedName(), getMode(stack));
	}
}
