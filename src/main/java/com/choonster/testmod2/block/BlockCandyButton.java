package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.init.ModItems;
import net.minecraft.block.BlockButton;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCandyButton extends BlockButton {
	public static final String[] COLOURS = new String[]{"red", "orange", "yellow", "green", "lblue", "dblue", "purple", "pink"};

	public BlockCandyButton(int colourIndex) {
		super(false);
		setBlockName("candyButton." + COLOURS[colourIndex]);
		//setBlockTextureName(References.MODID + ":" + this.getUnlocalizedName().substring(5));

		if (colourIndex == 0) {
			setCreativeTab(TestMod2.tab);
		}
	}

	// We override these three methods so all colours of button will use the same item instead of each using their own item

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return ModItems.candyButton;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return ModItems.candyButton;
	}

	@Override
	protected ItemStack createStackedBlock(int p_149644_1_) {
		return new ItemStack(ModItems.candyButton, 1, 0);
	}

	@Override
	public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
		return Blocks.bedrock.getBlockTextureFromSide(0);
	}
}
