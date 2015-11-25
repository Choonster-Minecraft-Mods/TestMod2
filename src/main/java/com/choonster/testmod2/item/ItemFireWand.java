package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemFireWand extends Item {
	public ItemFireWand() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("firewand");
		setTextureName("stick");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (!world.isRemote) {
			Vec3 vec3 = player.getLookVec();
			EntityLargeFireball fireball = new EntityLargeFireball(world, player, vec3.xCoord, vec3.yCoord, vec3.zCoord);
			world.spawnEntityInWorld(fireball);
		}

		int posX = (int) player.posX, posY = (int) player.posY, posZ = (int) player.posZ;
		world.setBlock(posX, posY, posZ, Blocks.command_block);
		TileEntityCommandBlock tileEntity = (TileEntityCommandBlock) world.getTileEntity(posX, posY, posZ);
		tileEntity.func_145993_a().setCommand("say hello");

		return stack;
	}
}
