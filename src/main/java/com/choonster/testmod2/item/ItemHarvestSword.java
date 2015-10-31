package com.choonster.testmod2.item;

import com.choonster.testmod2.TestMod2;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
	 * Can this tool harvest the {@link Block}?
	 * <p>
	 * This should only be used by {@link EntityPlayer#isCurrentToolAdventureModeExempt(int, int, int)}, use the tool class/harvest level system where possible.
	 *
	 * @param block     The Block
	 * @param itemStack The tool
	 * @return Can this tool harvest the Block?
	 */
	@Override
	public boolean canHarvestBlock(Block block, ItemStack itemStack) {
		return EFFECTIVE_MATERIALS.contains(block.getMaterial());
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		// Not all blocks have a harvest tool/level set, so we need to fall back to checking the Material like the vanilla tools do
		if (ForgeHooks.isToolEffective(stack, block, meta) || EFFECTIVE_MATERIALS.contains(block.getMaterial())) {
			return efficiencyOnProperMaterial;
		}
		return super.getDigSpeed(stack, block, meta);
	}

	public boolean hitEntity(ItemStack itemStack, EntityLivingBase target, EntityLivingBase attacker) {
		itemStack.damageItem(1, attacker); // Only reduce the durability by 1 point (like swords do) instead of 2 (like tools do)
		return true;
	}
}
