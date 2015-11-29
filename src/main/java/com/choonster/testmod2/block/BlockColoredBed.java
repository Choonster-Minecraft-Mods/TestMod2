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
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2280852-crash-custom-bed?page=2
public class BlockColoredBed extends BlockBed {
	private final int colorIndex;

	private IIcon[] topIcons;
	private IIcon[] endIcons;
	private IIcon[] sideIcons;

	public BlockColoredBed(int colorIndex) {
		super();
		this.colorIndex = colorIndex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		String prefix = References.MODID + ":" + this.getUnlocalizedName().substring(5) + "_" + References.COLORS[colorIndex];

		this.topIcons = new IIcon[]{iconRegister.registerIcon(prefix + "_feet_top"), iconRegister.registerIcon(prefix + "_head_top")};
		this.endIcons = new IIcon[]{iconRegister.registerIcon(prefix + "_feet_end"), iconRegister.registerIcon(prefix + "_head_end")};
		this.sideIcons = new IIcon[]{iconRegister.registerIcon(prefix + "_feet_side"), iconRegister.registerIcon(prefix + "_head_side")};
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
			int k = getDirection(meta);
			int l = Direction.bedDirection[k][side];
			int i1 = isBlockHeadOfBed(meta) ? 1 : 0;
			return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? this.topIcons[i1] : this.sideIcons[i1]) : this.endIcons[i1];
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