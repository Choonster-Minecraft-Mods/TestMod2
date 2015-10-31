package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.ForgeHooks;

import java.util.Collections;
import java.util.Set;

/**
 * A tool that can function as a sword, pickaxe, axe or shovel.
 * <p>
 * Test for this thread:
 * http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2550421-how-to-make-a-tool-e-g-a-sword-have-the-abilities
 */
public class ItemHarvestSword extends ItemTool {

	/**
	 * The speed at which Cobwebs are harvested
	 */
	public static final float DIG_SPEED_WEB = 15.0f;

	/**
	 * The speed at which Sword-effective {@link Material}s are harvested
	 */
	public static final float DIG_SPEED_SWORD = 1.5f;

	/**
	 * The speed at which blocks are harvested if this isn't their correct tool
	 */
	public static final float DIG_SPEED_DEFAULT = 1.0f;

	public ItemHarvestSword(ToolMaterial toolMaterial) {
		super(4.0f, toolMaterial, Collections.EMPTY_SET);
		setHarvestLevel("pickaxe", toolMaterial.getHarvestLevel());
		setHarvestLevel("axe", toolMaterial.getHarvestLevel());
		setHarvestLevel("shovel", toolMaterial.getHarvestLevel());
		setCreativeTab(TestMod2.tab);
	}

	/**
	 * The {@link Material}s that this tool is effective on.
	 */
	public static final Set<Material> EFFECTIVE_MATERIALS = ImmutableSet.of(
			// Pickaxe
			Material.rock, Material.iron, Material.ice, Material.glass, Material.piston, Material.anvil, Material.circuits,

			// Axe
			Material.wood, Material.gourd, Material.plants, Material.vine,

			// Shovel
			Material.grass, Material.ground, Material.sand, Material.snow, Material.craftedSnow, Material.clay
	);

	/**
	 * The {@link Material}s that Swords are effective on.
	 */
	public static final Set<Material> SWORD_MATERIALS = ImmutableSet.of(
			Material.plants, Material.vine, Material.coral, Material.leaves, Material.gourd
	);

	/**
	 * Can this tool harvest the {@link Block}?
	 * <p>
	 * This should only be used by {@link EntityPlayer#isCurrentToolAdventureModeExempt(int, int, int)} and {@link ForgeHooks#canHarvestBlock(Block, EntityPlayer, int)},
	 * use the tool class/harvest level system where possible.
	 *
	 * @param block     The Block
	 * @param itemStack The tool
	 * @return Is this tool effective on the {@link Block's} {@link Material}?
	 */
	@Override
	public boolean canHarvestBlock(Block block, ItemStack itemStack) {
		return EFFECTIVE_MATERIALS.contains(block.getMaterial()) || block == Blocks.web;
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		if (block == Blocks.web) {
			return DIG_SPEED_WEB;
		}

		// Not all blocks have a harvest tool/level set, so we need to fall back to checking the Material like the vanilla tools do
		if (ForgeHooks.isToolEffective(stack, block, meta) || EFFECTIVE_MATERIALS.contains(block.getMaterial())) {
			return efficiencyOnProperMaterial;
		}

		if (SWORD_MATERIALS.contains(block.getMaterial())) {
			return DIG_SPEED_SWORD;
		}

		return DIG_SPEED_DEFAULT;
	}

	public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker) {
		itemStack.damageItem(1, attacker); // Only reduce the durability by 1 point (like swords do) instead of 2 (like tools do)
		return true;
	}
}
