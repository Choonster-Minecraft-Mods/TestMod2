package com.choonster.testmod2.block;

import com.choonster.testmod2.References;
import com.choonster.testmod2.init.ItemRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBed;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockColoredBed extends BlockBed {
	private int colorIndex;

	private IIcon[] topIcons;
	private IIcon[] endIcons;
	private IIcon[] sideIcons;

	public BlockColoredBed(int colorIndex) {
		super();
		this.colorIndex = colorIndex;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
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
	public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
		return ItemRegistry.coloredBed;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return isBlockHeadOfBed(p_149650_1_) ? Item.getItemById(0) : ItemRegistry.coloredBed;
	}

	@Override
	public int damageDropped(int p_149692_1_) {
		return colorIndex;
	}
}