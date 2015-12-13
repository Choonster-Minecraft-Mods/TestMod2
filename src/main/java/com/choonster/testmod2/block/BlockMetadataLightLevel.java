package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * A block whose light level is controlled by metadata. Right click to get the current light level, shift-right click to increase it by 1 (wraps around to 0 after reaching 15).
 * <p>
 * Test for this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,35457.0.html
 */
public class BlockMetadataLightLevel extends Block {
	public BlockMetadataLightLevel() {
		super(Material.redstoneLight);
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("metadataLightLevel");
		setTextureName("redstone_lamp_on");
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public int damageDropped(int meta) {
		return 0;
	}

	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
		int currentMeta = worldIn.getBlockMetadata(x, y, z);

		if (player.isSneaking()) {
			int newMeta = (currentMeta + 1) % 16;

			if (worldIn.setBlockMetadataWithNotify(x, y, z, newMeta, 3) && !worldIn.isRemote) {
				player.addChatComponentMessage(new ChatComponentTranslation("message.testmod2.blockMetadataLightLevel.newLightLevel", newMeta));
			}
		} else if (!worldIn.isRemote) {
			player.addChatComponentMessage(new ChatComponentTranslation("message.testmod2.blockMetadataLightLevel.currentLightLevel", currentMeta));
		}

		return true;
	}
}
