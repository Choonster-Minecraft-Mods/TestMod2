package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

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
		if (!(entity instanceof EntityItem)) { // If the Entity isn't an EntityItem, return now
			return;
		}

		EntityItem entityItem = (EntityItem) entity; // Cast the Entity to EntityItem
		ItemStack stack = entityItem.getEntityItem(); // Get the EntityItem's ItemStack

		if (stack.getItem() == Items.quartz) { // If the stack contains Nether Quartz,
			double posX = entityItem.posX, posY = entityItem.posY, posZ = entityItem.posZ;

			if (!world.isRemote) { // If this is the server
				ItemStack newStack = stack.copy(); // Copy the stack
				newStack.func_150996_a(Items.baked_potato); // Replace its Item with Baked Potato

				EntityItem newEntityItem = new EntityItem(world, posX, posY, posZ, newStack); // Create a new EntityItem at the same position
				world.spawnEntityInWorld(newEntityItem); // Spawn it

				entityItem.setDead(); // Kill the old one
			}

			world.playSoundAtEntity(entityItem, "game.tnt.primed", 1.0F, 1.0F); // Play a sound

			for (int i = 0; i < 32; ++i) { // Spawn some particles
				world.spawnParticle("portal", posX, posY + world.rand.nextDouble() * 2.0D, posZ, world.rand.nextGaussian(), 0.0D, world.rand.nextGaussian());
			}
		}
	}
}
