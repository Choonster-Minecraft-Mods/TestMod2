package com.choonster.testmod2.item;

import com.choonster.testmod2.init.Entities;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A copy of ItemMonsterPlacer that uses entity names instead of global IDs
 */
public class ItemModMonsterPlacer extends Item {
	@SideOnly(Side.CLIENT)
	private IIcon theIcon;

	public ItemModMonsterPlacer() {
		setHasSubtypes(true);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("monsterPlacer");
		setTextureName("spawn_egg");
	}

	/**
	 * Get the name of the entity spawned by an ItemStack.
	 *
	 * @param stack The ItemStack
	 * @return The entity's name
	 */
	private static String getEntityName(ItemStack stack) {
		return stack.hasTagCompound() ? stack.getTagCompound().getString("EntityName") : null;
	}

	/**
	 * Set the name of the entity spawned by an ItemStack.
	 *
	 * @param stack      The ItemStack
	 * @param entityName The entity's name
	 * @return The ItemStack
	 */
	private static ItemStack setEntityName(ItemStack stack, String entityName) {
		stack.setTagInfo("EntityName", new NBTTagString(entityName));
		return stack;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		String displayName = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String entityName = getEntityName(stack);

		if (entityName != null) {
			displayName = displayName + " " + StatCollector.translateToLocal("entity." + entityName + ".name");
		}

		return displayName;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		Entities.ModEntityEggInfo entityegginfo = Entities.getEntityEggInfo(getEntityName(stack));
		return entityegginfo != null ? (renderPass == 0 ? entityegginfo.primaryColor : entityegginfo.secondaryColor) : 16777215;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		} else {
			Block block = world.getBlock(x, y, z);
			x += Facing.offsetsXForSide[side];
			y += Facing.offsetsYForSide[side];
			z += Facing.offsetsZForSide[side];

			double yOffset = 0.0D;

			if (side == 1 && block.getRenderType() == 11) {
				yOffset = 0.5D;
			}

			Entity entity = spawnCreature(world, getEntityName(stack), x + 0.5D, y + yOffset, z + 0.5D);

			if (entity != null) {
				if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
					((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
				}

				if (!player.capabilities.isCreativeMode) {
					--stack.stackSize;
				}
			}

			return true;
		}
	}


	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote) {
			return stack;
		} else {
			MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);

			if (movingobjectposition == null) {
				return stack;
			} else {
				if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
					int x = movingobjectposition.blockX;
					int y = movingobjectposition.blockY;
					int z = movingobjectposition.blockZ;

					if (!world.canMineBlock(player, x, y, z)) {
						return stack;
					}

					if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, stack)) {
						return stack;
					}

					if (world.getBlock(x, y, z) instanceof BlockLiquid) {
						Entity entity = spawnCreature(world, getEntityName(stack), x, y, z);

						if (entity != null) {
							if (entity instanceof EntityLivingBase && stack.hasDisplayName()) {
								((EntityLiving) entity).setCustomNameTag(stack.getDisplayName());
							}

							if (!player.capabilities.isCreativeMode) {
								--stack.stackSize;
							}
						}
					}
				}

				return stack;
			}
		}
	}

	/**
	 * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
	 */
	public static Entity spawnCreature(World world, String entityName, double x, double y, double z) {
		if (Entities.getEntityEggInfo(entityName) == null) {
			return null;
		} else {
			Entity entity = null;

			for (int j = 0; j < 1; ++j) {
				entity = EntityList.createEntityByName(entityName, world);

				if (entity != null && entity instanceof EntityLiving) {
					EntityLiving entityliving = (EntityLiving) entity;
					entity.setLocationAndAngles(x, y, z, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
					entityliving.rotationYawHead = entityliving.rotationYaw;
					entityliving.renderYawOffset = entityliving.rotationYaw;
					entityliving.onSpawnWithEgg(null);
					world.spawnEntityInWorld(entity);
					entityliving.playLivingSound();
				}
			}

			return entity;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int metadata, int renderPass) {
		return renderPass > 0 ? this.theIcon : super.getIconFromDamageForRenderPass(metadata, renderPass);
	}

	@Override
	@SuppressWarnings("unchecked")
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs creativeTab, List itemsList) {
		// Remove any vanilla spawn eggs with metadata 0
		itemsList.removeIf(stack -> ((ItemStack) stack).getItem() == Items.spawn_egg && ((ItemStack) stack).getItemDamage() == 0);

		// Create a list of mod spawn egg ItemStacks from the registered eggs
		List<ItemStack> eggs = Entities.getEntityEggs().keySet().stream().map(name -> setEntityName(new ItemStack(item, 1), name)).collect(Collectors.toList());

		// Add the eggs list to the items list
		itemsList.addAll(eggs);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		super.registerIcons(iconRegister);
		this.theIcon = iconRegister.registerIcon(this.getIconString() + "_overlay");
	}
}
