package com.choonster.testmod2.item.block;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.block.BlockColoredBed;
import com.choonster.testmod2.init.BlockRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2280852-crash-custom-bed
public class ItemColoredBed extends ItemBed {

	@SideOnly(Side.CLIENT)
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
	public void registerIcons(IIconRegister iconRegister) {
		for (int i = 0; i < bedIcons.length; i++) {
			this.bedIcons[i] = iconRegister.registerIcon(this.getIconString() + "_" + References.COLORS[i]);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, @SuppressWarnings("rawtypes") List itemList) {
		for (int j = 0; j < 16; ++j) {
			itemList.add(new ItemStack(item, 1, j));
		}
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = MathHelper.clamp_int(stack.getItemDamage(), 0, 15);
		return super.getUnlocalizedName() + "." + References.COLORS[i];
	}

	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else if (side != 1) {
			return false;
		} else {
			++y;

			int colorIndex = stack.getItemDamage();
			BlockColoredBed coloredBed = BlockRegistry.coloredBeds[colorIndex];

			int i1 = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			byte offsetX = 0;
			byte offsetZ = 0;

			if (i1 == 0) {
				offsetZ = 1;
			}

			if (i1 == 1) {
				offsetX = -1;
			}

			if (i1 == 2) {
				offsetZ = -1;
			}

			if (i1 == 3) {
				offsetX = 1;
			}

			if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x + offsetX, y, z + offsetZ, side, stack)) {
				if (world.isAirBlock(x, y, z) && world.isAirBlock(x + offsetX, y, z + offsetZ) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + offsetX, y - 1, z + offsetZ)) {
					world.setBlock(x, y, z, coloredBed, i1, 3);

					if (world.getBlock(x, y, z) == coloredBed) {
						world.setBlock(x + offsetX, y, z + offsetZ, coloredBed, i1 + 8, 3);
					}

					--stack.stackSize;
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