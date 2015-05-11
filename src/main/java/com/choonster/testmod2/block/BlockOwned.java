package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.tileentity.TileEntityOwned;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2424209-owned-blocks#c9
public class BlockOwned extends BlockContainer {
	public BlockOwned() {
		super(Material.rock);
		setCreativeTab(TestMod2.tab);
		setBlockName("owned");
		setBlockTextureName("minecraft:bedrock");
		setHardness(2.0f);
		setHarvestLevel("pickaxe", 1);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
		TileEntityOwned tileEntityOwned = (TileEntityOwned) world.getTileEntity(x, y, z);
		tileEntityOwned.setOwnerUUID(entity.getUniqueID());

		if (entity instanceof ICommandSender && !world.isRemote) {
			((ICommandSender) entity).addChatMessage(new ChatComponentTranslation("message.owned.placed", x, y, z));
		}
	}

	private boolean entityOwnsBlock(World world, int x, int y, int z, EntityLivingBase entity) {
		TileEntityOwned tileEntityOwned = (TileEntityOwned) world.getTileEntity(x, y, z);

		return entity.getUniqueID().equals(tileEntityOwned.getOwnerUUID());
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest) {
		if (entityOwnsBlock(world, x, y, z, player)) {
			return super.removedByPlayer(world, player, x, y, z, willHarvest);
		} else if (!world.isRemote) {
			player.addChatComponentMessage(new ChatComponentTranslation("message.owned.notOwned"));
		}

		return false;
	}


	@Override
	public float getPlayerRelativeBlockHardness(EntityPlayer player, World world, int x, int y, int z) {
		if (entityOwnsBlock(world, x, y, z, player)) {
			return super.getPlayerRelativeBlockHardness(player, world, x, y, z);
		} else {
			return -1.0f;
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			if (entityOwnsBlock(world, x, y, z, player)) {
				player.addChatComponentMessage(new ChatComponentTranslation("message.owned.rightClick", player.getDisplayName()));
			} else {
				player.addChatComponentMessage(new ChatComponentTranslation("messsage.owned.notOwned"));
			}
		}

		return super.onBlockActivated(world, x, y, z, player, metadata, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityOwned();
	}
}
