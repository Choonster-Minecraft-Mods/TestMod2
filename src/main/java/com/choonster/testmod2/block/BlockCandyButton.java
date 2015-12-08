package com.choonster.testmod2.block;

import com.choonster.testmod2.References;
import com.choonster.testmod2.init.ModBlocks;
import com.choonster.testmod2.init.ModItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockButton;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

/**
 * A coloured button.
 */
public class BlockCandyButton extends BlockButton {
	public static final String[] COLOURS = new String[]{"red", "orange", "yellow", "green", "lblue", "dblue", "purple", "pink"};

	public final int colourIndex;

	public BlockCandyButton(int colourIndex) {
		super(false);
		this.colourIndex = colourIndex;
		setUnlocalizedName("candyButton." + COLOURS[colourIndex]);
		setTextureName(References.RESOURCE_PREFIX + ModBlocks.getStrippedName(this));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int x, int y, int z) {
		return ModItems.candyButton;
	}

	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
		return ModItems.candyButton;
	}

	@Override
	protected ItemStack createStackedBlock(int meta) {
		return new ItemStack(ModItems.candyButton, 1, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister reg) {
		this.blockIcon = reg.registerIcon(this.getTextureName());
	}
}
