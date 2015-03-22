package com.choonster.testmod2.item;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.block.BlockColoredBed;
import com.choonster.testmod2.init.BlockRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

//import com.millstone.MillstoneMod;
//import com.millstone.lib.References;
//import com.millstone.registry.BlockRegistry;

public class ItemColoredBed extends ItemBed {

	//@SideOnly(Side.CLIENT)
	private final IIcon[] bedIcons = new IIcon[References.COLORS.length];

	public ItemColoredBed() {
		super();
		this.setHasSubtypes(true); // This allows the item to be marked as a metadata item. // This makes it so the item doesn't have the damage bar at the bottom of its icon.
		this.setMaxStackSize(1);
		this.setUnlocalizedName("coloredBed");
		this.setTextureName(References.MODID + ":" + getUnlocalizedName().substring(5));
		this.setCreativeTab(TestMod2.tab);
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		int j = MathHelper.clamp_int(meta, 0, 15);
		return this.bedIcons[j];
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister) {
		for (int i = 0; i < bedIcons.length; i++) {
			this.bedIcons[i] = par1IconRegister.registerIcon(this.getIconString() + "_" + References.COLORS[i]);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTabs, @SuppressWarnings("rawtypes") List par3List) {
		for (int j = 0; j < 16; ++j) {
			par3List.add(new ItemStack(par1Item, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int i = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + References.COLORS[i];
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
	 */
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (p_77648_3_.isRemote) {
			return true;
		} else if (p_77648_7_ != 1) {
			return false;
		} else {
			++p_77648_5_;

			int colorIndex = p_77648_1_.getItemDamage();
			BlockColoredBed coloredBed = (BlockColoredBed) BlockRegistry.coloredBeds[colorIndex];

			int i1 = MathHelper.floor_double((double) (p_77648_2_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			byte b0 = 0;
			byte b1 = 0;

			if (i1 == 0) {
				b1 = 1;
			}

			if (i1 == 1) {
				b0 = -1;
			}

			if (i1 == 2) {
				b1 = -1;
			}

			if (i1 == 3) {
				b0 = 1;
			}

			if (p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_) && p_77648_2_.canPlayerEdit(p_77648_4_ + b0, p_77648_5_, p_77648_6_ + b1, p_77648_7_, p_77648_1_)) {
				if (p_77648_3_.isAirBlock(p_77648_4_, p_77648_5_, p_77648_6_) && p_77648_3_.isAirBlock(p_77648_4_ + b0, p_77648_5_, p_77648_6_ + b1) && World.doesBlockHaveSolidTopSurface(p_77648_3_, p_77648_4_, p_77648_5_ - 1, p_77648_6_) && World.doesBlockHaveSolidTopSurface(p_77648_3_, p_77648_4_ + b0, p_77648_5_ - 1, p_77648_6_ + b1)) {
					p_77648_3_.setBlock(p_77648_4_, p_77648_5_, p_77648_6_, coloredBed, i1, 3);

					if (p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_) == coloredBed) {
						p_77648_3_.setBlock(p_77648_4_ + b0, p_77648_5_, p_77648_6_ + b1, coloredBed, i1 + 8, 3);
					}

					--p_77648_1_.stackSize;
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

}