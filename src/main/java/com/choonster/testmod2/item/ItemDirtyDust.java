package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.util.ChatUtils;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

import java.util.List;

// http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2406366-1-7-10-how-to-make-item-change-when-it-touches
public class ItemDirtyDust extends Item {
	public ItemDirtyDust() {
		setCreativeTab(TestMod2.tab);
		setUnlocalizedName("dirtyDust");
		setTextureName("redstone_dust");
	}

	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List tooltipLines, boolean advancedTooltips) {
		tooltipLines.add("Does stuff when thrown in water");
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		// If on the server, check if the EntityItem is in water
		if (!entityItem.worldObj.isRemote && entityItem.isInsideOfMaterial(Material.water)) {
			ChatUtils.sendServerMessage(new ChatComponentTranslation("Item in water! %s,%s,%s", entityItem.posX, entityItem.posY, entityItem.posZ));
		}

		return super.onEntityItemUpdate(entityItem);
	}
}
