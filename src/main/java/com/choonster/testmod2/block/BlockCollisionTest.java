package com.choonster.testmod2.block;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.TestMod2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCollisionTest extends Block {

	public BlockCollisionTest() {
		super(Material.glass);
		setLightOpacity(0);
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("testmod2:collisiontest");
		setTextureName("minecraft:glass");
		setHardness(0.5F);
		//setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List boundingBoxList, Entity entity) {
		if (!world.isRemote) {
			Logger.debug("collisionBoxes %d,%d,%d - %d - %s", x, y, z, boundingBoxList.size(), entity);
		}

		if (((entity instanceof EntityPlayer)) &&
				(!entity.isSneaking())) {
			return;
		}

		if (entity instanceof EntityItem) {
			return;
		}

//		setBlockBounds(0, 0, 0, 1, 1, 1);
//		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, boundingBoxList, entity);
//		setBlockBounds(0, 0, 0, 0, 0, 0);
	}

//	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
//	{
//		//return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1, par4 + 1);
//		return null;
//	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return par1IBlockAccess.getBlock(par2, par3, par4) != this;
	}

	@Override
	public int damageDropped(int p_149692_1_) {
		return p_149692_1_;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return false;
	}
}
