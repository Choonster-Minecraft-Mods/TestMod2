package com.choonster.testmod2.block;

import com.choonster.testmod2.References;
import com.choonster.testmod2.init.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * A coloured bed.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2280852-crash-custom-bed?page=2
 */
public class BlockColoredBed extends BlockBed {
	private final int colorIndex;

	private IIcon[] topIcons;
	private IIcon[] endIcons;
	private IIcon[] sideIcons;

	public BlockColoredBed(int colorIndex) {
		super();
		this.colorIndex = colorIndex;
		setUnlocalizedName("coloredBed." + ItemDye.dyeIcons[colorIndex]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String vanillaPrefix = "minecraft:bed";
		String colorPrefix = References.RESOURCE_PREFIX + this.getUnlocalizedName().replaceFirst("tile\\.", "").replaceFirst("\\.", "_");

		this.topIcons = new IIcon[]{iconRegister.registerIcon(colorPrefix + "_feet_top"), iconRegister.registerIcon(vanillaPrefix + "_head_top")};
		this.endIcons = new IIcon[]{iconRegister.registerIcon(vanillaPrefix + "_feet_end"), iconRegister.registerIcon(vanillaPrefix + "_head_end")};
		this.sideIcons = new IIcon[]{iconRegister.registerIcon(vanillaPrefix + "_feet_side"), iconRegister.registerIcon(vanillaPrefix + "_head_side")};
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (side == 0) {
			return Blocks.planks.getBlockTextureFromSide(side);
		} else {
			int direction = getDirection(meta);
			int bedDirection = Direction.bedDirection[direction][side];
			int iconIndex = isBlockHeadOfBed(meta) ? 1 : 0;
			return (iconIndex != 1 || bedDirection != 2) && (iconIndex != 0 || bedDirection != 3) ? (bedDirection != 5 && bedDirection != 4 ? this.topIcons[iconIndex] : this.sideIcons[iconIndex]) : this.endIcons[iconIndex];
		}
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return ModItems.coloredBed;
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int fortune) {
		return isBlockHeadOfBed(metadata) ? Item.getItemById(0) : ModItems.coloredBed;
	}

	@Override
	public int damageDropped(int metadata) {
		return colorIndex;
	}

	@Override
	public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player) {
		return true;
	}
}