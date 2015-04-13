package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.tileentity.TileEntityDisplayNameTest;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2404916-need-help-with-display-names
public class BlockDisplayNameTest extends Block implements ITileEntityProvider {
	public BlockDisplayNameTest() {
		super(Material.rock);
		setBlockTextureName("bedrock");
		setBlockName("displayNameTest");
		setCreativeTab(TestMod2.tab);
	}

	private TileEntityDisplayNameTest getTileEntity(World world, int x, int y, int z) {
		return (TileEntityDisplayNameTest) world.getTileEntity(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityPlacedBy, ItemStack itemStackPlacedBy) {
		if (!world.isRemote) {
			String displayName = itemStackPlacedBy.getDisplayName();

			TileEntityDisplayNameTest te = getTileEntity(world, x, y, z);
			te.setDisplayName(displayName);
			te.markDirty();

			if (entityPlacedBy instanceof ICommandSender) {
				((ICommandSender) entityPlacedBy).addChatMessage(new ChatComponentTranslation("Placed display name: %s", displayName));
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntityDisplayNameTest te = getTileEntity(world, x, y, z);
			String displayName = te.getDisplayName();
			player.addChatMessage(new ChatComponentTranslation("Clicked display name: %s", displayName));
		}

		return super.onBlockActivated(world, x, y, z, player, metadata, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileEntityDisplayNameTest();
	}
}
