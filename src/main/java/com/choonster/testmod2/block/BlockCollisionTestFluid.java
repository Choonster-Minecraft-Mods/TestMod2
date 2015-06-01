package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.util.ChatUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.text.SimpleDateFormat;
import java.util.Date;

// A fluid Block that sends a server-wide chat message when an EntityItem collides with it
// Test for this thread: http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2439366-help-with-have-items-be-swaped-with-other-items
public class BlockCollisionTestFluid extends BlockFluidClassic {
	public BlockCollisionTestFluid(Fluid fluid) {
		super(fluid, Material.water);
		setCreativeTab(TestMod2.tab);
		setBlockName(fluid.getUnlocalizedName());
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.flowing_water.getIcon(side, meta);
	}

	@Override
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {
		return 90019001;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
		if (entity instanceof EntityItem && !world.isRemote) {
			ChatUtils.sendServerMessage("Collision! %s", new SimpleDateFormat("HH:mm:ss").format(new Date()));
		}
	}
}
