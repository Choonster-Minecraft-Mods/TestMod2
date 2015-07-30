package com.choonster.testmod2.init;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntityArmouredSkeleton;
import com.choonster.testmod2.entity.EntityBarrelBomb;
import com.choonster.testmod2.entity.EntityModChicken;
import com.choonster.testmod2.entity.EntityZombieTest;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;

public class Entities {
	private static int entityID = 0;

	public static void preInit() {
		registerMobEntityWithSpawnEgg(EntityZombieTest.class, "TestZombie", 96, 23, 173, 14, 2, 235);
		registerProjectileEntity(EntityBarrelBomb.class, "BarrelBomb");
		registerMobEntityWithSpawnEgg(EntityArmouredSkeleton.class, "ArmouredSkeleton", 66, 66, 66, 88, 88, 88);
		registerMobEntityWithSpawnEgg(EntityModChicken.class, "Chicken", 161, 161, 161, 255, 0, 0);

		EntityRegistry.addSpawn(EntityArmouredSkeleton.class, 1000, 1, 10, EnumCreatureType.monster, ModBiomes.biomeGenTest);
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
		// registerModEntity automatically prefixes the entity name with the mod ID, do the same here for consistency.
		EntityRegistry.registerGlobalEntityID(entityClass, References.MODID + "." + entityName, EntityRegistry.findGlobalUniqueEntityId(), getRGBInt(bgRed, bgGreen, bgBlue), getRGBInt(fgRed, fgGreen, fgBlue));
		registerMobEntity(entityClass, entityName);
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
		registerEntity(entityClass, entityName, 80, 3, true);
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
		registerEntity(entityClass, entityName, 64, 10, true);
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
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, TestMod2.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
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
}
