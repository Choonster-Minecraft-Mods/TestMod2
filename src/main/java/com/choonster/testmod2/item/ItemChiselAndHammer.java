package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

// perplexedmodder17 - http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2400460-entityitems-not-spawning-correctly-please-help
public class ItemChiselAndHammer extends Item {
	public ItemChiselAndHammer() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("chiselAndHammer");
		setTextureName("minecraft:gold_ingot");
	}

	@Override
	public boolean onItemUse(ItemStack chisel, EntityPlayer player, World world, int blockx, int blocky, int blockz, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
		if (!player.canPlayerEdit(blockx, blocky, blockz, p_77648_7_, chisel)) {
			return false;
		} else {
			/*UseChiselEvent event = new UseChiselEvent(player, chisel, world, blockx, blocky, blockz);
			if (MinecraftForge.EVENT_BUS.post(event))
			{
				return false;
			}

			if (event.getResult() == Event.Result.ALLOW)
			{
				chisel.damageItem(1, player);
				return true;
			}*/

			Block block = world.getBlock(blockx, blocky, blockz);

			if (p_77648_7_ != 0 && block == Blocks.clay) {
				Block block1 = Blocks.stone;
				world.playSoundEffect((double) ((float) blockx + 0.5F), (double) ((float) blocky + 0.5F), (double) ((float) blockz + 0.5F), block1.stepSound.getStepSound(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getFrequency() * 0.8F);

				if (world.isRemote) {
					return true;
				} else {
					world.setBlock(blockx, blocky, blockz, block1);
					chisel.damageItem(1, player);
					ItemStack MineralStack = new ItemStack(Items.bed);
					float f = 0.7F;
					double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
					EntityItem entityitem = new EntityItem(world, (double) blockx + d0, (double) blocky + d1, (double) blockz + d2, MineralStack);
					entityitem.delayBeforeCanPickup = 10;
					world.spawnEntityInWorld(entityitem);
					return true;
				}
			} else {
				return false;
			}
		}
	}
}
