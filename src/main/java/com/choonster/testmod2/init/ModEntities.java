package com.choonster.testmod2.init;

import com.choonster.testmod2.Logger;
import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.*;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.stats.StatBase;
import net.minecraft.util.ChatComponentTranslation;

import java.lang.reflect.Field;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class ModEntities {

	public static void preInit() {
		registerMobEntityWithSpawnEgg(EntityZombieTest.class, "TestZombie", 96, 23, 173, 14, 2, 235);
		registerProjectileEntity(EntityBarrelBomb.class, "BarrelBomb");
		registerMobEntityWithSpawnEgg(EntityArmouredSkeleton.class, "ArmouredSkeleton", 66, 66, 66, 88, 88, 88);
		registerMobEntityWithSpawnEgg(EntityModChicken.class, "Chicken", 161, 161, 161, 255, 0, 0);
		registerProjectileEntity(EntityModPotion.class, "ModPotion");

		EntityRegistry.addSpawn(EntityArmouredSkeleton.class, 1000, 1, 10, EnumCreatureType.monster, ModBiomes.biomeGenTest);
	}

	public static Map<String, ModEntityEggInfo> getEntityEggs() {
		return INSTANCE.ENTITY_EGGS;
	}

	public static ModEntityEggInfo getEntityEggInfo(Entity entity) {
		return getEntityEggInfo(EntityList.getEntityString(entity));
	}

	public static ModEntityEggInfo getEntityEggInfo(String entityName) {
		return INSTANCE.ENTITY_EGGS.get(entityName);
	}

	/**
	 * Register a standard mob or animal entity with a spawn egg.
	 * <p>
	 * Uses the same tracking values as IAnimals (implemented by most mobs and animals).
	 *
	 * @param entityClass The entity's class
	 * @param entityName  The entity's unique name
	 * @param bgRed       The red component of the spawn egg's background colour
	 * @param bgGreen     The green component of the spawn egg's background colour
	 * @param bgBlue      The blue component of the spawn egg's background colour
	 * @param fgRed       The red component of the spawn egg's foreground colour
	 * @param fgGreen     The green component of the spawn egg's foreground colour
	 * @param fgBlue      The blue component of the spawn egg's foreground colour
	 */
	private static void registerMobEntityWithSpawnEgg(Class<? extends Entity> entityClass, String entityName, int bgRed, int bgGreen, int bgBlue, int fgRed, int fgGreen, int fgBlue) {
		registerMobEntity(entityClass, entityName);
		// registerModEntity automatically prefixes the entity name with the mod ID, do the same here for consistency.
		INSTANCE.registerSpawnEgg(entityClass, References.MODID + "." + entityName, getRGBInt(bgRed, bgGreen, bgBlue), getRGBInt(fgRed, fgGreen, fgBlue));
	}

	/**
	 * Register a standard mob or animal entity.
	 * <p>
	 * Uses the same tracking values as IAnimals (implemented by most mobs and animals).
	 *
	 * @param entityClass The entity's class
	 * @param entityName  The entity's unique name
	 */
	private static void registerMobEntity(Class<? extends Entity> entityClass, String entityName) {
		INSTANCE.registerEntity(entityClass, entityName, 80, 3, true);
	}

	/**
	 * Register a projectile entity.
	 * <p>
	 * Uses the same tracking values as most vanilla projectiles.
	 *
	 * @param entityClass The entity's class
	 * @param entityName  The entity's unique name
	 */
	private static void registerProjectileEntity(Class<? extends Entity> entityClass, String entityName) {
		INSTANCE.registerEntity(entityClass, entityName, 64, 10, true);
	}

	/**
	 * Register an entity with the specified tracking values.
	 *
	 * @param entityClass          The entity's class
	 * @param entityName           The entity's unique name
	 * @param trackingRange        The range at which MC will send tracking updates
	 * @param updateFrequency      The frequency of tracking updates
	 * @param sendsVelocityUpdates Whether to send velocity information packets as well
	 */
	private void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, TestMod2.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

	/**
	 * The instance.
	 */
	private static final ModEntities INSTANCE = new ModEntities();

	/**
	 * The next mod-specific ID to use.
	 */
	private int entityID = 0;

	/**
	 * The used global IDs greater than 255.
	 * <p>
	 * The bit at index N will be true if the ID N+256 has been used.
	 */
	private final BitSet usedIDs;

	/**
	 * The entity spawn eggs.
	 */
	private final Map<String, ModEntityEggInfo> ENTITY_EGGS = new HashMap<>();

	private ModEntities() {
		//EntityRegistry.instance(); // Make sure EntityRegistry has been loaded

		usedIDs = new BitSet(256);
		for (Object idObject : EntityList.idToClassMap.keySet()) {
			int id = (int) idObject;
			if (id > 255) {
				usedIDs.set(id - 256);
			}
		}
	}

	/**
	 * Find the next available global ID greater than 255.
	 *
	 * @return The available ID
	 * @throws RuntimeException If there are no free IDs
	 */
	private int findAvailableGlobalID() {
		int index = usedIDs.nextClearBit(0);

		while (true) {
			usedIDs.set(index); // Mark the index as used

			int id = index + 256;

			if (!EntityList.idToClassMap.containsKey(id) && !EntityList.entityEggs.containsKey(id)) { // If the ID is free, return it
				return id;
			}

			if (id == Short.MAX_VALUE) {
				break; // ItemStack metadata (used by spawn eggs as the entity ID) is saved as a short
			}

			index = usedIDs.nextClearBit(index + 1);
		}

		throw new RuntimeException("No free global entity ID was found! Do you really have 2^15-257 entities using global IDs greater than 255?");
	}

	private Field stringToIDMappingsField = ReflectionHelper.findField(EntityList.class, "field_75622_f", "stringToIDMapping");

	/**
	 * Register an spawn egg.
	 *
	 * @param entityClass The entity's class
	 * @param entityName  The entity's unique name
	 * @param bgColour    The spawn egg's background colour
	 * @param fgColour    The spawn egg's foreground colour
	 */
	private void registerSpawnEgg(Class<? extends Entity> entityClass, String entityName, int bgColour, int fgColour) {
		int id = findAvailableGlobalID();
		ModEntityEggInfo entityEggInfo = new ModEntityEggInfo(entityName, bgColour, fgColour);
		EntityList.entityEggs.put(id, entityEggInfo);
		ENTITY_EGGS.put(entityName, entityEggInfo);

		try {
			((Map<String, Integer>) stringToIDMappingsField.get(null)).put(entityName, id);
		} catch (IllegalAccessException e) {
			Logger.error(e, "Error registering spawn egg");
		}
	}

	/**
	 * Pack the specified RGB values (0-255) into a single int suitable for use in Item#getColorFromItemStack.
	 *
	 * @param r The red component
	 * @param g The green component
	 * @param b The blue component
	 * @return The packed int
	 */
	private static int getRGBInt(int r, int g, int b) {
		return (r << 16) + (g << 8) + b;
	}

	public static class ModEntityEggInfo extends EntityList.EntityEggInfo {
		private static Field killedStatField = ReflectionHelper.findField(EntityList.EntityEggInfo.class, "field_151512_d");
		private static Field killedByStatField = ReflectionHelper.findField(EntityList.EntityEggInfo.class, "field_151513_e");

		private static StatBase createStat(String entityName, String statName, String messageTranslationKey) {
			return new StatBase(String.format(statName, entityName), new ChatComponentTranslation(messageTranslationKey, new ChatComponentTranslation("entity." + entityName + ".name"))).registerStat();
		}

		public final String entityName;

		public ModEntityEggInfo(String entityName, int primaryColour, int secondaryColour) {
			super(-1, primaryColour, secondaryColour);
			this.entityName = entityName;
			try {
				killedStatField.set(this, createStat(entityName, "stat.killEntity.%s", "stat.entityKill"));
				killedByStatField.set(this, createStat(entityName, "stat.entityKilledBy.%s", "stat.entityKilledBy"));
			} catch (IllegalAccessException e) {
				Logger.error(e, "Error creating stats for spawn egg");
			}
		}
	}
}
