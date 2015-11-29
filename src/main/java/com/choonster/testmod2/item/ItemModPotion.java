package com.choonster.testmod2.item;

import com.choonster.testmod2.entity.EntityModPotion;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension of the vanilla potion item that correctly calculates the liquid colour for potions using the CustomPotionEffects tag list.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2559988-crafting-unimplemented-potions-like-the-potion-of
 */
public class ItemModPotion extends ItemPotion {
	/**
	 * The default duration of potions in ticks (3 minutes)
	 */
	private static final int DEFAULT_DURATION = 3 * 60 * 20;

	/**
	 * The duration multiplier for Tier II potions
	 */
	public static final double DURATION_MULTIPLIER_TIER_2 = 0.5;

	/**
	 * The duration multiplier for Extended potions
	 */
	public static final double DURATION_MULTIPLIER_EXTENDED = 8.0 / 3.0;

	/**
	 * The duration multiplier for Splash potions
	 */
	public static final double DURATION_MULTIPLIER_SPLASH = 0.75;

	/**
	 * Get the default duration of the specified {@link Potion} ({@link #DEFAULT_DURATION} * {@link Potion#getEffectiveness()})
	 *
	 * @param potion The potion
	 * @return The default duration
	 */
	public static double getDefaultDuration(Potion potion) {
		return DEFAULT_DURATION * potion.getEffectiveness();
	}


	/**
	 * A cache mapping CustomPotionEffects tag lists to their corresponding liquid colour
	 */
	private final Map<NBTTagList, Integer> colourCache = new HashMap<>();

	/**
	 * A list of custom potions to use as sub-items
	 */
	private final List<ItemStack> customPotions = new ArrayList<>();

	public ItemModPotion() {
		super();
		setUnlocalizedName("potion");
		setTextureName("potion");
	}

	/**
	 * Does the ItemStack have a CustomPotionEffects tag list?
	 *
	 * @param itemStack The ItemStack
	 * @return Does the ItemStack have a CustomPotionEffects tag list?
	 */
	private boolean hasCustomEffectsTagList(ItemStack itemStack) {
		return itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("CustomPotionEffects", Constants.NBT.TAG_LIST);
	}

	/**
	 * Get the ItemStack's CustomPotionEffects tag list. Assumes the tag is present, use {@link #hasCustomEffectsTagList(ItemStack)} to check.
	 *
	 * @param itemStack The ItemStack
	 * @return The ItemStack's CustomPotionEffects tag list
	 */
	private NBTTagList getCustomEffectsTagList(ItemStack itemStack) {
		return itemStack.getTagCompound().getTagList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND);
	}

	@SuppressWarnings({"unchecked", "EmptyMethod"})
	@Override
	public List<PotionEffect> getEffects(ItemStack p_77832_1_) {
		return super.getEffects(p_77832_1_);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack itemStack, int renderPass) {
		if (renderPass == 0 && hasCustomEffectsTagList(itemStack)) {
			NBTTagList customEffectsListTag = getCustomEffectsTagList(itemStack);
			if (colourCache.containsKey(customEffectsListTag)) {
				return colourCache.get(customEffectsListTag);
			} else {
				List<PotionEffect> effects = getEffects(itemStack);
				int colour = PotionHelper.calcPotionLiquidColor(effects);
				colourCache.put(customEffectsListTag, colour);
				return colour;
			}
		}

		return super.getColorFromItemStack(itemStack, renderPass);
	}

	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, List itemList) {
		itemList.addAll(customPotions);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		// If this is a splash potion, spawn an EntityModPotion so the correct liquid colour is used
		if (isSplash(itemStack.getMetadata())) {
			if (!player.capabilities.isCreativeMode) {
				--itemStack.stackSize;
			}

			world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!world.isRemote) {
				world.spawnEntityInWorld(new EntityModPotion(world, player, itemStack));
			}

			return itemStack;
		}

		return super.onItemRightClick(itemStack, world, player);
	}

	/**
	 * Get a potion {@link ItemStack} with the specified {@link PotionEffect}s. The {@link ItemStack} is used a sub-item.
	 *
	 * @param isSplash      Should the potion be a splash potion?
	 * @param potionEffects The potion effects to apply
	 * @return The potion {@link ItemStack}
	 */
	public ItemStack getCustomPotion(boolean isSplash, PotionEffect... potionEffects) {
		NBTTagList customEffectsTagList = new NBTTagList();
		for (PotionEffect potionEffect : potionEffects) {
			customEffectsTagList.appendTag(potionEffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
		}

		ItemStack potion = new ItemStack(this, 1, 1 << (isSplash ? 14 : 13));
		potion.setTagInfo("CustomPotionEffects", customEffectsTagList);

		customPotions.add(potion);

		return potion;
	}
}
