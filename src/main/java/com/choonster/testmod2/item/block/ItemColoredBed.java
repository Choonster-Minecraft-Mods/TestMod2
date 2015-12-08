package com.choonster.testmod2.item.block;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.block.BlockColoredBed;
import com.choonster.testmod2.init.ModBlocks;
import com.choonster.testmod2.init.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * A coloured bed.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2280852-crash-custom-bed?page=2
 */
public class ItemColoredBed extends ItemBed {

	private final IIcon[] bedIcons = new IIcon[ItemDye.dyeIcons.length];

	public ItemColoredBed() {
		super();
		this.setHasSubtypes(true); // This allows the item to be marked as a metadata item. // This makes it so the item doesn't have the damage bar at the bottom of its icon.
		this.setMaxStackSize(1);
		this.setUnlocalizedName("coloredBed");
		this.setTextureName(References.RESOURCE_PREFIX + ModItems.getStrippedName(this));
		this.setCreativeTab(TestMod2.tab);
	}

	private int getColorIndex(int meta) {
		return MathHelper.clamp_int(meta, 0, 15);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIconFromDamage(int meta) {
		int colorIndex = getColorIndex(meta);
		return this.bedIcons[colorIndex];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		for (int i = 0; i < bedIcons.length; i++) {
			this.bedIcons[i] = iconRegister.registerIcon(this.getIconString() + "_" + ItemDye.dyeIcons[i]);
		}
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List itemList) {
		for (int i = 0; i < 16; ++i) {
			itemList.add(new ItemStack(item, 1, i));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int colorIndex = getColorIndex(stack.getMetadata());
		return super.getUnlocalizedName() + "." + ItemDye.dyeIcons[colorIndex];
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		// If this is the server and the player clicked the top of a block,
		if (!world.isRemote && ForgeDirection.getOrientation(side) == ForgeDirection.UP) {
			++y;

			int colorIndex = getColorIndex(stack.getMetadata());
			BlockColoredBed coloredBed = ModBlocks.coloredBeds[colorIndex];

			// Get the direction of the bed's head from the player's yaw rotation
			int headDirection = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

			// Convert the 2D direction to the corresponding 3D ForgeDirection
			ForgeDirection headOrientation = ForgeDirection.getOrientation(Direction.directionToFacing[headDirection]);

			// Get the x and z offsets of the head block from the foot block
			int offsetX = headOrientation.offsetX;
			int offsetZ = headOrientation.offsetZ;

			if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x + offsetX, y, z + offsetZ, side, stack)) {
				if (world.isAirBlock(x, y, z) && world.isAirBlock(x + offsetX, y, z + offsetZ) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + offsetX, y - 1, z + offsetZ)) {
					world.setBlock(x, y, z, coloredBed, headDirection, 3); // Place the foot block

					if (world.getBlock(x, y, z) == coloredBed) { // If the foot was placed,
						world.setBlock(x + offsetX, y, z + offsetZ, coloredBed, headDirection + 8, 3); // Place the head block
					}

					--stack.stackSize;
					return true;
				}
			}
		}

		return false;
	}

}