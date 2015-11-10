package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

/**
 * An item that can be used with a Can Opener in the inventory to obtain an Open Can of the appropriate food.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2555968-making-a-consumable
 */
public class ItemCannedFood extends Item {

	/**
	 * The {@link Item} required to open this can
	 */
	protected Item canOpener;

	/**
	 * The {@link ItemStack} to give to the player when they open this can
	 */
	protected ItemStack openCan;

	public ItemCannedFood() {
		setCreativeTab(TestMod2.tab);
	}

	/**
	 * Get the {@link Item} required to open this can
	 *
	 * @return The Can Opener
	 */
	public Item getCanOpener() {
		return canOpener;
	}

	/**
	 * Set the {@link Item} required to open this can
	 *
	 * @param canOpener The Can Opener
	 * @return This instance
	 */
	public ItemCannedFood setCanOpener(Item canOpener) {
		this.canOpener = canOpener;
		return this;
	}

	/**
	 * Get a copy of the {@link ItemStack} to give to the player when they open this can
	 *
	 * @return The Open Can
	 */
	public ItemStack getOpenCan() {
		return openCan.copy();
	}

	/**
	 * Set the {@link ItemStack} to give to the player when they open this can
	 *
	 * @param openCan The Open Can
	 * @return This instance
	 */
	public ItemCannedFood setOpenCan(ItemStack openCan) {
		this.openCan = openCan;
		return this;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return EnumAction.block;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack itemStack) {
		return 32;
	}

	/**
	 * Can the player open this can?
	 *
	 * @param cannedFood The Canned Food
	 * @param player     The player
	 * @return {@code true} if the player can open this can
	 */
	protected boolean canPlayerOpenCan(ItemStack cannedFood, EntityPlayer player) {
		return player.capabilities.isCreativeMode || player.inventory.hasItem(canOpener);
	}

	/**
	 * Damage the Can Opener
	 *
	 * @param cannedFood The Canned Food
	 * @param player     The player
	 */
	protected void damageCanOpener(ItemStack cannedFood, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode) { // If the player isn't in Creative Mode
			ItemStack[] mainInventory = player.inventory.mainInventory;
			for (int i = 0; i < mainInventory.length; i++) {// Iterate through their inventory
				ItemStack itemStack = mainInventory[i];
				if (itemStack != null && itemStack.getItem() == canOpener) { // If this item is the Can Opener,
					itemStack.damageItem(1, player); // Damage it
					if (itemStack.stackSize == 0) { // If it was destroyed,
						mainInventory[i] = null; // Clear the inventory slot
						ForgeEventFactory.onPlayerDestroyItem(player, itemStack); // Fire PlayerDestroyItemEvent
					}
					return;
				}
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (canPlayerOpenCan(itemStack, player)) { // If the player can open this can,
			player.setItemInUse(itemStack, getMaxItemUseDuration(itemStack)); // Start using it
		}

		return itemStack;
	}

	@Override
	public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
		if (canPlayerOpenCan(itemStack, player)) { // If the player can open this can,
			damageCanOpener(itemStack, player); // Damage the Can Opener

			ItemStack openCan = getOpenCan(); // Get the Open Can

			--itemStack.stackSize; // Consume one of this can
			if (itemStack.stackSize <= 0) { // If that was the last one,
				return openCan; // Return the Open Can
			} else if (!player.inventory.addItemStackToInventory(openCan)) { // Else try to add the Open Can to the player's inventory
				player.dropPlayerItemWithRandomChoice(openCan, false); // If that doesn't work, drop it on the ground
			}
		}

		return itemStack;
	}
}
