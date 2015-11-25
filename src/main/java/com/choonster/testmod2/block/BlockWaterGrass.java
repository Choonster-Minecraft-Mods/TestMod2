package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Tall grass that renders with water around it while in water.
 * <p>
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,32280.0.html
 */
public class BlockWaterGrass extends BlockBush {
	private static final double RENDER_TEMPERATURE = 0.5, RENDER_HUMIDITY = 1.0;

	public BlockWaterGrass() {
		super(Material.water);
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("watergrass");
		setTextureName("tallgrass");
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
	}

	@Override
	public boolean canReplace(World world, int x, int y, int z, int side, ItemStack stack) {
		return world.getBlock(x, y + 1, z) == Blocks.water && canBlockStay(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int silkTouch) {
		world.setBlock(x, y, z, Blocks.water);
	}

	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return ColorizerGrass.getGrassColor(RENDER_TEMPERATURE, RENDER_HUMIDITY);
	}

	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return ColorizerGrass.getGrassColor(RENDER_TEMPERATURE, RENDER_HUMIDITY);
	}

	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return blockAccess.getBiomeGenForCoords(x, z).getBiomeGrassColor(x, y, z);
	}
}
