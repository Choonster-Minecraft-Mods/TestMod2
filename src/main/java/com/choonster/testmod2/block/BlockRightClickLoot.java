package com.choonster.testmod2.block;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.init.ModLoot;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * A block that breaks and spawns a random item when right clicked.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2562992-giving-a-random-item-on-a-block-right-click
 */
public class BlockRightClickLoot extends Block {
	public BlockRightClickLoot() {
		super(Material.iron);
		setUnlocalizedName("rightClickLoot");
		setTextureName("bookshelf");
		setCreativeTab(TestMod2.tab);
	}

	@Override
	public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
		if (!worldIn.isRemote) {
			ItemStack itemStack = ModLoot.RIGHT_CLICK_LOOT_BLOCK.getOneItem(worldIn.rand); // Get a random item from the loot list
			dropBlockAsItem(worldIn, x, y, z, itemStack); // Drop it in the world

			player.playSound("random.break", 0.8F, 0.8F + worldIn.rand.nextFloat() * 0.4F); // Play the tool break sound
			worldIn.setBlockToAir(x, y, z); // Set this block to air
		}

		return true;
	}
}
