package com.choonster.testmod2.entity;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;

/**
 * A zombie-based entity that tells you how many times you've killed and been killed by its entity type when right clicked.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2540043-using-player-stats-to-affect-mob-behavior
 */
public class EntityKillCountTest extends EntityZombie {
	private static final int ARMOUR_COLOUR = 0x5f56cf;

	public EntityKillCountTest(World world) {
		super(world);
	}

	@Override
	protected void addRandomArmor() {
		setCurrentItemOrArmor(0, new ItemStack(Items.enchanted_book)); // Equip an Enchanted Book in the hand

		for (int slot = 4; slot > 0; slot--) { // For each armour slot,
			ItemStack stack = new ItemStack(getArmorItemForSlot(slot, 0)); // Get the Leather Armour item for this slot
			((ItemArmor) stack.getItem()).func_82813_b(stack, ARMOUR_COLOUR); // Colour it blue
			setCurrentItemOrArmor(slot, stack); // Equip it
		}
	}

	/**
	 * Send a kill count chat message to the player
	 *
	 * @param player         The player
	 * @param translationKey The message's translation key
	 * @param count          The kill count
	 */
	private void sendKillCountChatMessage(EntityPlayer player, String translationKey, int count) {
		if (count > 0) {
			player.addChatComponentMessage(new ChatComponentTranslation(translationKey, count));
		} else {
			player.addChatComponentMessage(new ChatComponentTranslation(translationKey + ".none"));
		}
	}

	@Override
	public boolean interact(EntityPlayer player) {
		if (!worldObj.isRemote) {
			StatFileWriter statFile = ((EntityPlayerMP) player).getStatFile();

			int id = EntityList.getEntityID(this);
			EntityList.EntityEggInfo entityEggInfo = (EntityList.EntityEggInfo) EntityList.entityEggs.get(id);

			int killCount = statFile.writeStat(entityEggInfo.field_151512_d);
			sendKillCountChatMessage(player, "message.testmod2.entityKillCountTest.killCount", killCount);

			int killedByCount = statFile.writeStat(entityEggInfo.field_151513_e);
			sendKillCountChatMessage(player, "message.testmod2.entityKillCountTest.killedByCount", killedByCount);
		}

		return true;
	}
}
