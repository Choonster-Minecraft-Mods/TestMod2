package com.choonster.testmod2.entity;

import com.google.common.base.Throwables;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class EntityModPotion extends EntityPotion implements IEntityAdditionalSpawnData {
	private static final Field POTION_FIELD = ReflectionHelper.findField(EntityPotion.class, "potionDamage", "field_70197_d");

	public EntityModPotion(World world) {
		super(world);
	}

	public EntityModPotion(World world, EntityLivingBase thrower, int potionDamage) {
		super(world, thrower, potionDamage);
	}

	public EntityModPotion(World world, EntityLivingBase thrower, ItemStack potionItemStack) {
		super(world, thrower, potionItemStack);
	}

	@SideOnly(Side.CLIENT)
	public EntityModPotion(World world, double x, double y, double z, int potionDamage) {
		super(world, x, y, z, potionDamage);
	}

	public EntityModPotion(World world, double x, double y, double z, ItemStack potionItemStack) {
		super(world, x, y, z, potionItemStack);
	}

	public ItemStack getPotion() {
		try {
			return (ItemStack) POTION_FIELD.get(this);
		} catch (IllegalAccessException e) {
			Throwables.propagate(e);
			return null;
		}
	}

	public void setPotion(ItemStack itemStack) {
		try {
			POTION_FIELD.set(this, itemStack);
		} catch (IllegalAccessException e) {
			Throwables.propagate(e);
		}
	}


	@Override
	public void writeSpawnData(ByteBuf buffer) {
		ByteBufUtils.writeItemStack(buffer, getPotion());
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		setPotion(ByteBufUtils.readItemStack(additionalData));
	}
}
