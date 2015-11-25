package com.choonster.testmod2.block;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.TestMod2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * A falling block (like Sand/Gravel) that only collides with falling block entities.
 * <p>
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,33313.0.html
 */
public class BlockFallingNoCollision extends BlockFalling {
	public BlockFallingNoCollision() {
		super(Material.glass);
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("fallingNoCollision");
		setTextureName("minecraft:glass_black");
		setLightLevel(1);
	}

	// Transparency methods
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
		return world.getBlock(x, y, z) != this && super.shouldSideBeRendered(world, x, y, z, side);
	}

	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return 1;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	// Collision methods
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List boundingBoxList, Entity entity) {
		if (entity instanceof EntityFallingBlock) {
			Logger.info("Falling block collision!");
			super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, boundingBoxList, entity);
		}
	}
}
