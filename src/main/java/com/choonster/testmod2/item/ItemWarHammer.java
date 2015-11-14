package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.util.EnchantmentUtil;
import com.google.common.collect.Multimap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * A weapon that gains the Knockback II enchantment and slows down the player when held and automatically removes the enchantment (if it's an equal or lesser level) when not held.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2558880-help-adding-enchant-to-item-when-held-and-remove
 */
public class ItemWarHammer extends ItemSword {

	/**
	 * The {@link UUID} of the speed modifier
	 */
	public static final UUID SPEED_MODIFIER_ID = UUID.fromString("1735a950-ce32-4300-9dcb-8773516aa94d");

	/**
	 * The amount of the speed modifier (same as {@link Potion#moveSlowdown})
	 */
	public static final double SPEED_MODIFIER_AMOUNT = -0.15;

	/**
	 * The operation of the speed modifier (same as {@link Potion#moveSlowdown})
	 */
	public static final int SPEED_MODIFIER_OPERATION = 2;

	/**
	 * The level of Knockback to apply
	 */
	public static final int KNOCKBACK_LEVEL = 2;

	public ItemWarHammer(ToolMaterial toolMaterial) {
		super(toolMaterial);
		setCreativeTab(TestMod2.tab);
	}

	@Override
	public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean isHeld) {
		super.onUpdate(itemStack, world, entity, slot, isHeld);

		int currentKnockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, itemStack);

		// If the item is currently held and the current Knockback level is less than KNOCKBACK_LEVEL
		// Or it's not held and the current Knockback level is less than or equal to KNOCKBACK_LEVEL
		if ((isHeld && currentKnockbackLevel < KNOCKBACK_LEVEL) || (!isHeld && currentKnockbackLevel <= KNOCKBACK_LEVEL)) {
			// Remove any level of Knockback from it
			EnchantmentUtil.removeEnchantment(Enchantment.knockback, itemStack);

			// If the item is currently held, add the desired level of Knockback to it
			if (isHeld) {
				itemStack.addEnchantment(Enchantment.knockback, KNOCKBACK_LEVEL);
			}
		}
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack) {
		@SuppressWarnings("unchecked")
		Multimap<String, AttributeModifier> attributeModifiers = super.getAttributeModifiers(stack);
		attributeModifiers.put(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), new AttributeModifier(SPEED_MODIFIER_ID, "War Hammer Speed Modifier", SPEED_MODIFIER_AMOUNT, SPEED_MODIFIER_OPERATION));
		return attributeModifiers;
	}
}
