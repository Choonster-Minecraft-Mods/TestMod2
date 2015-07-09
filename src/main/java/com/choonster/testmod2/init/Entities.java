package com.choonster.testmod2.init;

import com.choonster.testmod2.References;
import com.choonster.testmod2.TestMod2;
import com.choonster.testmod2.entity.EntityArmouredSkeleton;
import com.choonster.testmod2.entity.EntityBarrelBomb;
import com.choonster.testmod2.entity.EntityZombieTest;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.entity.Entity;

public class Entities {
	private static int entityID = 0;

	public static void preInit() {
		registerEntity(EntityZombieTest.class, "TestZombie", 96, 23, 173, 14, 2, 235);
		registerEntity(EntityBarrelBomb.class, "BarrelBomb");
		registerEntity(EntityArmouredSkeleton.class, "ArmouredSkeleton", 66, 66, 66, 88, 88, 88);
	}

	// Register an Entity with a spawn egg
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int bgRed, int bgGreen, int bgBlue, int fgRed, int fgGreen, int fgBlue) {
		// registerModEntity automatically prefixes the entity name with the mod ID, do the same here for consistency.
		EntityRegistry.registerGlobalEntityID(entityClass, References.MODID + "." + entityName, EntityRegistry.findGlobalUniqueEntityId(), getRGBInt(bgRed, bgGreen, bgBlue), getRGBInt(fgRed, fgGreen, fgBlue));
		registerEntity(entityClass, entityName);
	}

	// Register an Entity without a spawn egg
	private static void registerEntity(Class<? extends Entity> entityClass, String entityName) {
		EntityRegistry.registerModEntity(entityClass, entityName, entityID++, TestMod2.instance, 64, 3, true);
	}

	private static int getRGBInt(int rInt, int gInt, int bInt) {
		return (rInt << 16) + (gInt << 8) + bInt;
	}
}
